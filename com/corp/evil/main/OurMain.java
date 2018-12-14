public class OurMain {

    public static void main(String[] args) throws ActivityAlreadyRegisteredException, MemberAlreadyRegisteredException, ActivityIsNullException, MemberIsNullException, NameIsEmptyException {
        run();
    }

    public static void run() throws ActivityAlreadyRegisteredException, MemberAlreadyRegisteredException, ActivityIsNullException, MemberIsNullException, NameIsEmptyException {
        ProjectTesting.createTestProject();
    }
}
