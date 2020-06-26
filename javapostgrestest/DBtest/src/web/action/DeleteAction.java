package web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LogicException;

public class DeleteAction extends BaseAction {
	String path;
			
	public void setPath(String path) {
		this.path = path;}

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		String idsString[] = req.getParameterValues("id");
		if(idsString != null) {
			try {
				List<Long> ids = new ArrayList<>(idsString.length);
				for(String id : idsString) {
					ids.add(Long.parseLong(id));
				}
				getService().delete(ids);
			} catch(NumberFormatException e) {
				throw new ActionException(e, 400);
			}
		}
		return new Result("/" + this.path + "/list");
	}

}
