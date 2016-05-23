class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

        group "/api", {
            "/users"(controller: 'user', action: 'create', method: 'POST')
            "/users"(controller: 'user', action: 'index', method: 'GET')
            "/users/$id"(controller: 'user', action: 'show', method: 'GET')
        }
    }
}
