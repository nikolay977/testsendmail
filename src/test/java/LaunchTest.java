import com.codeborne.selenide.Configuration;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"stepdefinitions"}

)

@RunWith(Cucumber.class)
public class LaunchTest {

    @BeforeClass
    public static void setUp() {

        Configuration.timeout = 60000;
        Configuration.startMaximized = true;

    }

}

