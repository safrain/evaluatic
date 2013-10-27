package com.github.safrain.evaluatic.groovy

import com.github.safrain.evaluatic.support.SpringSupport

/**
 * Spring framework support
 */
class Beans {
}
Beans.metaClass.static.propertyMissing = { String name ->
    SpringSupport.applicationContext.getBean(name);
}