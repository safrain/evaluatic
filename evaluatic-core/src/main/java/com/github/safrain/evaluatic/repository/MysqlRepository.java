package com.github.safrain.evaluatic.repository;

import com.github.safrain.evaluatic.SourceCode;
import com.github.safrain.evaluatic.exception.SourceCodeModificationFailedException;
import com.github.safrain.evaluatic.exception.SourceCodeNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MysqlRepository implements SourceCodeRepository {

	private JdbcTemplate jdbcTemplate;

	private String tableName = "evaluatic_source";

	@Override
	public boolean exists(String name) {
		return 1 == jdbcTemplate.queryForInt("SELECT count(1) FROM " + tableName + " WHERE source_name = ?", new Object[]{name});
	}

	@Override
	public SourceCode get(String name) throws SourceCodeNotFoundException {
		jdbcTemplate.queryForObject("SELECT * FROM " + tableName + " WHERE source_name = ?", new Object[]{name}, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SourceCode sourceCode = new SourceCode();
				sourceCode.setName(rs.getString("source_name"));
				sourceCode.setSource(rs.getString("source_content"));
				return sourceCode;
			}
		});
		return (SourceCode) jdbcTemplate.queryForObject("SELECT * FROM " + tableName + "WHERE source_name = ?", new Object[]{name}, SourceCode.class);
	}

	@Override
	public void create(SourceCode sourceCode) throws SourceCodeModificationFailedException {
		if (1 != jdbcTemplate.update("INSERT INTO " + tableName + "(source_name,source_content,gmt_create,gmt_modified) VALUES(?, ?, NOW(), NOW())", new Object[]{sourceCode.getName(), sourceCode.getSource()})) {
			throw new SourceCodeModificationFailedException(sourceCode.getName());
		}
	}

	@Override
	public void update(SourceCode sourceCode) throws SourceCodeModificationFailedException {
		if (1 != jdbcTemplate.update("UPDATE " + tableName + " SET source_content = ?, gmt_modified = NOW() WHERE source_name = ?s", new Object[]{sourceCode.getSource(), sourceCode.getName()})) {
			throw new SourceCodeModificationFailedException(sourceCode.getName());
		}
	}

	@Override
	public void delete(String name) throws SourceCodeModificationFailedException {
		if (1 != jdbcTemplate.update("DELETE FROM " + tableName + " WHERE source_name = ?", new Object[]{name})) {
			throw new SourceCodeModificationFailedException(name);
		}
	}

	@Override
	public List<String> list() {
		return jdbcTemplate.queryForList("SELECT source_name FROM " + tableName, String.class);
	}


	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
