package de.dkutzer.mywiremock;


public class App
{
    public static void main( String[] args )
    {
        PersonDTO p = new PersonDTO("Ringo", "Starr", 1);
        WebservicePersonController.create(p);
        final PersonDTO ringo = WebservicePersonController.get("Ringo");
        System.out.println(ringo);
        WebservicePersonController.delete(ringo);
    }
}
