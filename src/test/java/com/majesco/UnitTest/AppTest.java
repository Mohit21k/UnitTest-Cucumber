package com.majesco.UnitTest;
import com.cucumber.listener.ExtentProperties;
import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:features/"},
        glue = {"com.majesco.demo","stepdef"},
        tags = {"@Functional"},
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/report.html"})
public class AppTest {
    @BeforeClass
    public static void setup() {
        ExtentProperties extentProperties = ExtentProperties.INSTANCE;
//        extentProperties.setExtentXServerUrl("http://localhost:1337");
//        extentProperties.setProjectName("MyProject");
        extentProperties.setReportPath("target/myreport.html");
    }

    @AfterClass
    public static void teardown() throws IOException {

        //Reporter.loadXMLConfig(new File("config/extent-config.xml"));
        Reporter.setSystemInfo("user", System.getProperty("user.name"));
        Reporter.setSystemInfo("os", "Windows");
        Reporter.setTestRunnerOutput("Sample test runner output message");
    }
}
