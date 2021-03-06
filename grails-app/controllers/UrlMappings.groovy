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
            "/users/profile"(controller: 'user', action: 'profile', method: 'GET')

            "/dealers"(controller: 'dealer', action: 'save', method: 'POST')
            "/dealers"(controller: 'dealer', action: 'index', method: 'GET')
            "/dealers/$id"(controller: 'dealer', action: 'show', method: 'GET')

            "/locations"(controller: 'building', action: 'locations', method: 'GET')

            "/buildings"(controller: 'building', action: 'save', method: 'POST')
            "/buildings"(controller: 'building', action: 'index', method: 'GET')
            "/buildings/$id"(controller: 'building', action: 'show', method: 'GET')
            "/buildings/$id"(controller: 'building', action: 'update', method: 'PUT')

            "/buildings/$buildingId/comments"(controller: 'comment', action: 'save', method: 'POST')
            "/buildings/$buildingId/comments"(controller: 'comment', action: 'index', method: 'GET')

            "/buildings/$buildingId/contacts"(controller: 'contact', action: 'index', method: 'GET')
            "/buildings/$buildingId/contacts"(controller: 'contact', action: 'save', method: 'POST')
            "/buildings/$buildingId/contacts/$id"(controller: 'contact', action: 'delete', method: 'DELETE')

            "/positions"(controller: 'position', action: 'save', method: 'POST')
            "/positions"(controller: 'position', action: 'index', method: 'GET')
            "/positions/$id"(controller: 'position', action: 'show', method: 'GET')
            "/positions/$id"(controller: 'position', action: 'delete', method: 'DELETE')
            "/positions/$id"(controller: 'position', action: 'update', method: 'PUT')

            "/messages"(controller: 'message', action: 'save', method: 'POST')
            "/messages"(controller: 'message', action: 'index', method: 'GET')
            "/messages/$id"(controller: 'message', action: 'show', method: 'GET')

            "/dictionaries"(controller: 'dictionary', action: 'index', method: 'GET')
            "/dictionaries/$key"(controller: 'dictionary', action: 'show', method: 'GET')
            "/dictionaries/$key/items"(controller: 'dictionaryItem', action: 'index', method: 'GET')
            "/dictionaries/$key/count"(controller: 'dictionaryItem', action: 'countItems', method: 'GET')
            "/dictionaries/$key/items"(controller: 'dictionaryItem', action: 'save', method: 'POST')
            "/dictionaries/$key/items/$id"(controller: 'dictionaryItem', action: 'delete', method: 'DELETE')
            "/dictionaries/items/$id"(controller: "dictionaryItem", action: "show", method: 'GET')
        }
    }
}
