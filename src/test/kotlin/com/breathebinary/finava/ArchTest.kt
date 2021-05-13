package com.breathebinary.finava

import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import org.junit.jupiter.api.Test

class ArchTest {

    @Test
    fun servicesAndRepositoriesShouldNotDependOnWebLayer() {

        val importedClasses = ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.breathebinary.finava")

        noClasses()
            .that()
            .resideInAnyPackage("com.breathebinary.finava.service..")
            .or()
            .resideInAnyPackage("com.breathebinary.finava.repository..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("..com.breathebinary.finava.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses)
    }
}
