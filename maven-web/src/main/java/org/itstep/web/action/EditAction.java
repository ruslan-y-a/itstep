package org.itstep.web.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.entities.Entity;
import org.itstep.help.Reflection;
import org.itstep.service.LogicException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class EditAction extends BaseAction {
	
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		try {
			@SuppressWarnings("unchecked")
			List<Entity> list = (List<Entity>)getService().findAll();
			req.setAttribute("list", list);
			
			Map<String,Object> daoMaps = Reflection.getDaos(getService());
			for(Map.Entry<String,Object> entry:  daoMaps.entrySet()) {
				req.setAttribute(entry.getKey(),entry.getValue());
	//			System.out.println(entry.getKey() + " " + entry.getValue());
			}
			
			String id = req.getParameter("id");
			if(id != null) {
				Entity entity = getService().read(Long.parseLong(id));
				if(entity == null) {
					throw new IllegalArgumentException();
				}
				req.setAttribute("entity", entity);
			}
			return null;
		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 404);
		}
	}
}
