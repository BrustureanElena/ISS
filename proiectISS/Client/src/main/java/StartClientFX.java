import controllers.AgentController;
import controllers.ComenziController;
import controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IServices;

public class StartClientFX extends Application {

    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");

        try{
            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
            IServices server=(IServices)factory.getBean("service");
            System.out.println("Obtained a reference to remote  server");

            //login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root=loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setContext(server);

            //bibliotecar
            FXMLLoader bLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
            Parent bRoot = bLoader.load();
            AgentController agentController = bLoader.getController();
            agentController.setContext(server);


            FXMLLoader cLoader = new FXMLLoader(getClass().getResource("/comenzi.fxml"));
            Parent cRoot = cLoader.load();
            ComenziController comenziController=cLoader.getController();
            comenziController.setContext(server);



            ctrl.setControllerAgent(agentController);
            agentController.setComenziController(comenziController);

            ctrl.setParents(bRoot);
            agentController.setParents(cRoot);
            primaryStage.setTitle("Firma");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        }catch (Exception e) {
            System.err.println("Firma Initialization exception:"+e);
            e.printStackTrace();
        }
    }
}

