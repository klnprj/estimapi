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
            "/users"(controller: 'user', action: 'save', method: 'POST')
            "/users"(controller: 'user', action: 'index', method: 'GET')
            "/users/$id"(controller: 'user', action: 'show', method: 'GET')

            "/dealers"(controller: 'dealer', action: 'save', method: 'POST')
            "/dealers"(controller: 'dealer', action: 'index', method: 'GET')
            "/dealers/$id"(controller: 'dealer', action: 'show', method: 'GET')

            "/buildings"(controller: 'building', action: 'save', method: 'POST')
            "/buildings"(controller: 'building', action: 'index', method: 'GET')
            "/buildings/$id"(controller: 'building', action: 'show', method: 'GET')
            "/buildings/$id"(controller: 'building', action: 'update', method: 'PUT')

            "/positions"(controller: 'position', action: 'save', method: 'POST')
            "/positions"(controller: 'position', action: 'index', method: 'GET')
            "/positions/$id"(controller: 'position', action: 'show', method: 'GET')
            "/positions/$id"(controller: 'position', action: 'delete', method: 'DELETE')

            "/messages"(controller: 'message', action: 'save', method: 'POST')
            "/messages"(controller: 'message', action: 'index', method: 'GET')
            "/messages/$id"(controller: 'message', action: 'show', method: 'GET')
        }
    }
}
