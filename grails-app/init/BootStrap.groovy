import com.estima.Dealer
import com.estima.User
import grails.util.Environment

class BootStrap {

    def sessionFactory

    def init = { servletContext ->

        if (Dealer.count() == 0) {
            new Dealer(name: 'Дилер 1').save(failOnError: true)
            new Dealer(name: 'Дилер 2').save(failOnError: true)
        }

        def session = sessionFactory?.currentSession

        if (User.count() == 0) {
            new User(name: 'Пользователь 1').save(failOnError: true)
            new User(name: 'Пользователь 2').save(failOnError: true)
        }

        Environment.executeForCurrentEnvironment {
            development {

            }
        }
    }
    def destroy = {
    }
}
