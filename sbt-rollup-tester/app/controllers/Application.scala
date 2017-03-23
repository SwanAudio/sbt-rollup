package controllers

import play.api.mvc.Action
import play.api.mvc.Controller

class Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

}
