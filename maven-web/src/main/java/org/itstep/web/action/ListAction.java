package org.itstep.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.entities.Entity;
import org.itstep.service.LogicException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class ListAction extends BaseAction {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	//	System.out.println("------===============================LIST)" + getService());
		@SuppressWarnings("unchecked")				
		List<Entity> list = (List<Entity>)getService().findAll();	
	//	System.out.println("------=============================LIST)" + list.size());
		req.setAttribute("list", list);
		return null;
	}
}
