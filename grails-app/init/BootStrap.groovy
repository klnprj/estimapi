import com.estima.Dealer
import com.estima.User
import grails.util.Environment

class BootStrap {

    def init = { servletContext ->

        Environment.executeForCurrentEnvironment {
            development {

                new Dealer(name: 'Дилер 1').save()
                new Dealer(name: 'Дилер 2').save()

                new User(name: 'Пользователь 1').save()
                new User(name: 'Пользователь 2').save()

            }
        }
    }
    def destroy = {
    }
}
