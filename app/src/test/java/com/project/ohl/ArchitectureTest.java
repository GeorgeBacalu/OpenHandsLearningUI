package com.project.ohl;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTest {
    private static final JavaClasses PRODUCTION = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS).importPackages("com.project.ohl");

    @Test public void domainIsIndependentOfAndroidAndDataLibraries() {
        classes().that().resideInAPackage("..domain..")
                .should().onlyDependOnClassesThat().resideInAnyPackage("java..", "com.project.ohl.domain..")
                .check(PRODUCTION);
    }

    @Test public void presentationUsesRepositoriesInsteadOfTransportOrStorageImplementations() {
        noClasses().that().resideInAPackage("..presentation..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("com.project.ohl.data..", "retrofit2..", "okhttp3..", "com.google.gson..", "androidx.room..")
                .check(PRODUCTION);
    }

    @Test public void dataDoesNotDependOnPresentationOrCompositionRoot() {
        noClasses().that().resideInAPackage("com.project.ohl.data..")
                .should().dependOnClassesThat().resideInAnyPackage("..presentation..", "com.project.ohl.app..")
                .check(PRODUCTION);
    }
}
