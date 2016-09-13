grails {
    profile = 'web'
    codegen {
        defaultPackage = 'com.klnprj'
    }
}

info {
    app {
        name = '@info.app.name@'
        version = '@info.app.version@'
        grailsVersion = '@info.app.grailsVersion@'
    }
}

spring {
    groovy {
        template['check-template-location'] = false
    }
}

hibernate {
    naming_strategy = 'org.hibernate.cfg.DefaultNamingStrategy'
    cache {
        queries = false
        use_second_level_cache = true
        use_query_cache = false
        region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory'
    }
}

grails {
    mime {
        disable {
            accept {
                header {
                    userAgents = ['Gecko', 'WebKit', 'Presto', 'Trident']
                }
            }
        }

        types {
            all = '*/*'
            atom = 'application/atom+xml'
            css = 'text/css'
            csv = 'text/csv'
            form = 'application/x-www-form-urlencoded'
            html = ['text/html', 'application/xhtml+xml']
            js = 'text/javascript'
            json = ['application/json', 'text/json']
            multipartForm = 'multipart/form-data'
            rss = 'application/rss+xml'
            text = 'text/plain'
            hal = ['application/hal+json', 'application/hal+xml']
            xml = ['text/xml', 'application/xml']
        }
    }
    urlmapping {
        cache {
            maxsize = 1000
        }
    }
    controllers {
        defaultScope = 'singleton'
    }
    converters {
        encoding = 'UTF-8'
    }
    views {
        'default' { codec = 'html' }//THIS WAS THE SOURCE OF ERROR
        gsp {
            encoding = 'UTF-8'
            htmlcodec = 'xml'
            codecs {
                expression = 'html'
                scriptlets = 'html'
                taglib = 'none'
                staticparts = 'none'
            }
        }
    }
}


def dbname = 'estimasys'
def dbusername = System.properties.getProperty('dbusername')
def dbpassword = System.properties.getProperty('dbpassword')

dataSource {
    pooled = true
    jmxExport = true
    driverClassName = 'org.h2.Driver'
    username = 'sa'
    password = ''
}

environments {
    development {
        dataSource {
            dbCreate = 'create-drop'
            url = 'jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
        }
    }
    staging {
        dataSource {
            dbCreate = 'validate'
            url = "jdbc:postgresql://127.0.0.1:5432/${dbname}"
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQL9Dialect
            username = dbusername
            password = dbpassword
        }
    }
    test {
        dataSource {
            dbCreate = 'update'
            url = 'jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE'
        }
    }
    production {
        dataSource {
            dbCreate = 'validate'
            url = "jdbc:postgresql://127.0.0.1:5432/${dbname}"
            driverClassName = "org.postgresql.Driver"
            dialect = org.hibernate.dialect.PostgreSQL9Dialect
            username = dbusername
            password = dbpassword
            properties {
                jmxEnabled = true
                initialSize = 5
                maxActive = 50
                minIdle = 5
                maxIdle = 25
                maxWait = 10000
                maxAge = 600000
                timeBetweenEvictionRunsMillis = 5000
                minEvictableIdleTimeMillis = 60000
                validationQuery = 'SELECT 1'
                validationQueryTimeout = 3
                validationInterval = 15000
                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false
                jdbcInterceptors = 'ConnectionState'
                defaultTransactionIsolation = 2 // TRANSACTION_READ_COMMITTED
            }
        }
    }
}

// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'com.estima.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'com.estima.auth.UserRole'
grails.plugin.springsecurity.authority.className = 'com.estima.auth.Role'
grails.plugin.springsecurity.userLookup.usernamePropertyName='email'
grails.plugin.springsecurity.userLookup.authoritiesPropertyName = 'authorities'
grails.plugin.springsecurity.authority.nameField = 'name'

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',                  access: ['permitAll']],
        [pattern: '/error',             access: ['permitAll']],
        [pattern: '/index',             access: ['permitAll']],
        [pattern: '/index.gsp',         access: ['permitAll']],
        [pattern: '/shutdown',          access: ['permitAll']],
        [pattern: '/assets/**',         access: ['permitAll']],
        [pattern: '/**/js/**',          access: ['permitAll']],
        [pattern: '/**/css/**',         access: ['permitAll']],
        [pattern: '/**/images/**',      access: ['permitAll']],
        [pattern: '/**/favicon.ico',    access: ['permitAll']],
        [pattern: '/oauth/authorize',	access: ['permitAll']],
        [pattern: '/oauth/token',		access: ['permitAll']],
]

grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/assets/**',         filters: 'none'],
        [pattern: '/**/js/**',          filters: 'none'],
        [pattern: '/**/css/**',         filters: 'none'],
        [pattern: '/**/images/**',      filters: 'none'],
        [pattern: '/**/favicon.ico',    filters: 'none'],
        [pattern: '/oauth/token',       filters: 'JOINED_FILTERS,-oauth2ProviderFilter,-securityContextPersistenceFilter,-logoutFilter,-authenticationProcessingFilter,-rememberMeAuthenticationFilter,-exceptionTranslationFilter'],
        [pattern: '/api/**',            filters: 'JOINED_FILTERS,-securityContextPersistenceFilter,-logoutFilter,-authenticationProcessingFilter,-rememberMeAuthenticationFilter,-oauth2BasicAuthenticationFilter,-exceptionTranslationFilter'],
        [pattern: '/**',                filters: 'JOINED_FILTERS,-statelessSecurityContextPersistenceFilter,-oauth2ProviderFilter,-clientCredentialsTokenEndpointFilter,-oauth2BasicAuthenticationFilter,-oauth2ExceptionTranslationFilter']
]

grails.plugin.springsecurity.providerNames = [
        'clientCredentialsAuthenticationProvider',
        'daoAuthenticationProvider'
]

// Added by the Spring Security OAuth2 Provider plugin:
grails.plugin.springsecurity.oauthProvider.clientLookup.className = 'com.estima.auth.Client'
grails.plugin.springsecurity.oauthProvider.authorizationCodeLookup.className = 'com.estima.auth.AuthorizationCode'
grails.plugin.springsecurity.oauthProvider.accessTokenLookup.className = 'com.estima.auth.AccessToken'
grails.plugin.springsecurity.oauthProvider.refreshTokenLookup.className = 'com.estima.auth.RefreshToken'
