package fi.hiit.dime.peoplefinder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import xdi2.core.ContextNode;
import xdi2.core.Graph;
import xdi2.core.LiteralNode;
import xdi2.core.syntax.XDIAddress;
import xdi2.core.util.iterators.IteratorListMaker;

public class ViewPeopleServlet extends HttpServlet implements ApplicationContextAware {

	private static final long serialVersionUID = 3793048689633131588L;

	public static final XDIAddress XDI_ADD_DIMEPROFILE = XDIAddress.create("#dime#profile");
	public static final XDIAddress XDI_ADD_TAGS = XDIAddress.create("[<#tag>]");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<ContextNode> list = new IteratorListMaker<ContextNode> (graph.getRootContextNode().getContextNodes()).list();
		JsonArray json = new JsonArray();

		for (Iterator<ContextNode> i = list.iterator(); i.hasNext(); ) {

			ContextNode c = i.next().getDeepContextNode(XDI_ADD_DIMEPROFILE);
			if (c == null) continue;

			JsonObject jsonProfile = new JsonObject();
			JsonArray jsonTags = new JsonArray();

			jsonProfile.addProperty("address", c.getXDIAddress().getFirstXDIArc().toString());

			for (LiteralNode l : c.getDeepContextNode(XDI_ADD_TAGS).getAllLiteralNodes())
				jsonTags.add(new JsonPrimitive(l.getLiteralDataString()));

			jsonProfile.add("tags", jsonTags);

			json.add(jsonProfile);
		}

		response.setContentType("application/json");
		response.getWriter().println(json.toString());
	}

	private static Graph graph;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		graph = (Graph) applicationContext.getBean("graph");
	}
}
