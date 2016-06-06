import com.estima.Dealer
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

        Environment.executeForCurrentEnvironment {
            development {

                new Dealer(name: 'Дилер 1').save()
                new Dealer(name: 'Дилер 2').save()
            }
        }
    }
    def destroy = {
    }
}
