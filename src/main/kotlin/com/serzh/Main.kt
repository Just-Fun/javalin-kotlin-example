package com.serzh
import com.serzh.data.User
import com.serzh.data.UserDao
import io.javalin.ApiBuilder
import io.javalin.Javalin

//https://javalin.io/tutorials/simple-kotlin-example
fun main(args: Array<String>) {

    val userDao = UserDao()

    val app = Javalin.create().apply {
        port(7000)
        exception(Exception::class.java) { e, ctx -> e.printStackTrace() }
        error(404) { ctx -> ctx.json("not found") }
    }.start()

    app.routes {

        //    It looks pretty similar to Java8:
//    Java8: get("/path", ctx -> { ... });
//    Kotlin: get("/path") { ctx -> ...}.
        ApiBuilder.get("/users") { ctx ->
            ctx.json(userDao.users)
        }

        ApiBuilder.get("/users/:id") { ctx ->
            ctx.json(userDao.findById(ctx.param("id")!!.toInt())!!)
        }

        ApiBuilder.get("/users/email/:email") { ctx ->
            ctx.json(userDao.findByEmail(ctx.param("email")!!)!!)
        }

        ApiBuilder.post("/users/create") { ctx ->
            val user = ctx.bodyAsClass(User::class.java)
            userDao.save(name = user.name, email = user.email)
            ctx.status(201)
        }

        ApiBuilder.patch("/users/update/:id") { ctx ->
            val user = ctx.bodyAsClass(User::class.java)
            userDao.update(
                    id = ctx.param("id")!!.toInt(),
                    user = user
            )
            ctx.status(204)
        }

        ApiBuilder.delete("/users/delete/:id") { ctx ->
            userDao.delete(ctx.param("id")!!.toInt())
            ctx.status(204)
        }

    }
}

