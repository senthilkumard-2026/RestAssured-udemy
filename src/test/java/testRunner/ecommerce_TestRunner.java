package testRunner;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	features = {"src/test/java/features/ecommerce_tests.feature"},
	glue = {"stepDefinitions"},
	monochrome = true,
	dryRun = false,
    plugin = {"pretty","html:target/cucumber-html-report.html"}
	 )
public class ecommerce_TestRunner extends AbstractTestNGCucumberTests {

}

