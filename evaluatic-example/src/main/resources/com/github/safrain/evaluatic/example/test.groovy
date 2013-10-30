package com.github.safrain.evaluatic.example

import com.github.safrain.evaluatic.SourceCode
import com.github.safrain.evaluatic.groovy.Beans
import com.github.safrain.evaluatic.groovy.Web
import com.github.safrain.evaluatic.repository.SourceCodeRepository

Web.println('hahahahaha')


Web.println Beans.mysqlRepository
SourceCodeRepository repository = Beans.mysqlRepository
Web.println repository.list()
SourceCode code = new SourceCode()
code.name = "test"
code.source = "hehe"
//repository.create(code)
//repository.delete("test")



Web.mav('a.vm', [a: 'ohohohoho'])
