package web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Entity;
import service.LogicException;

public class ListAction extends BaseAction {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		@SuppressWarnings("unchecked")
		List<Entity> list = (List<Entity>)getService().findAll();		
		req.setAttribute("list", list);
		return null;
	}
}
