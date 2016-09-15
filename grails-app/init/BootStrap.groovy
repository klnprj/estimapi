import com.estima.Dealer
import com.estima.DictionaryItem
import com.estima.auth.Role
import com.estima.User
import com.estima.Dictionary
import com.estima.auth.UserRole
import com.estima.auth.Client
import grails.util.Environment

class BootStrap {

    def sessionFactory

    def init = { servletContext ->

        def adminRole = Role.findByName('ROLE_ADMIN')
        def userRole = Role.findByName('ROLE_USER')
        def adminUser
        def testUser

        if (Role.count() == 0) {
            adminRole = new Role(name: 'ROLE_ADMIN').save()
            userRole = new Role(name: 'ROLE_USER').save()
        }

        if (User.count() == 0) {
            adminUser = new User(name: 'admin', email: 'admin@mail.ru', password: 'password').save(failOnError: true)
            testUser = new User(name: 'user', email: 'user@mail.ru', password: 'password').save(failOnError: true)

            UserRole.create adminUser, adminRole
            UserRole.create testUser, userRole

            UserRole.withSession {
                it.flush()
                it.clear()
            }
        }

        assert User.count() == 2
        assert Role.count() == 2
        assert UserRole.count() == 2

        if (Client.count() == 0) {
            new Client(
                    clientId: 'estima-client',
                    authorizedGrantTypes: ['refresh_token', 'password'],
                    authorities: ['ROLE_CLIENT'],
                    scopes: ['read', 'write'],
                    redirectUris: ['http://myredirect.com']
            ).save(flush: true)
        }

        if (Dictionary.count() == 0) {
            new Dictionary(key: 'customers', name: 'Заказчики').save(failOnError: true, flush: true)
            new Dictionary(key: 'designers', name: 'Проектные организации').save(failOnError: true, flush: true)
            new Dictionary(key: 'dealers', name: 'Дилеры').save(failOnError: true, flush: true)
            new Dictionary(key: 'contractors', name: 'Генеральные подрядчики').save(failOnError: true, flush: true)
            new Dictionary(key: 'subcontractors', name: 'Субподрядчики').save(failOnError: true, flush: true)
            new Dictionary(key: 'architects', name: 'Архитекторы').save(failOnError: true, flush: true)
        }

        Environment.executeForCurrentEnvironment {
            development {
                new DictionaryItem(dictionary: Dictionary.findByKey('customers'), title: 'Заказчик 1').save(failOnError: true, flush: true)
                new DictionaryItem(dictionary: Dictionary.findByKey('customers'), title: 'Заказчик 2').save(failOnError: true, flush: true)
                new DictionaryItem(dictionary: Dictionary.findByKey('designers'), title: 'Проектировщик 1').save(failOnError: true, flush: true)
                new DictionaryItem(dictionary: Dictionary.findByKey('designers'), title: 'Проектировщик 2').save(failOnError: true, flush: true)
                new DictionaryItem(dictionary: Dictionary.findByKey('dealers'), title: 'Дилер 1').save(failOnError: true, flush: true)
                new DictionaryItem(dictionary: Dictionary.findByKey('dealers'), title: 'Дилер 2').save(failOnError: true, flush: true)
            }
        }
    }
    def destroy = {
    }
}
