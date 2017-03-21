package fi.hiit.dime.peoplefinder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import xdi2.core.ContextNode;
import xdi2.core.Graph;
import xdi2.core.util.iterators.IteratorListMaker;

public class ViewPeopleServlet extends HttpServlet implements ApplicationContextAware {

	private static final long serialVersionUID = 3793048689633131588L;

	private static final Log log = LogFactory.getLog(ViewPeopleServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<ContextNode> list = new IteratorListMaker<ContextNode> (graph.getRootContextNode().getContextNodes()).list();
		
		for (Iterator<ContextNode> i = list.iterator(); i.hasNext(); ) {
			
			ContextNode c = i.next();
			if (! c.getXDIAddress().toString().startsWith("=!:uuid:")) i.remove();
		}

		request.setAttribute("list", list);
		request.getRequestDispatcher("viewpeople.jsp").forward(request, response);
	}

	private static Graph graph;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		graph = (Graph) applicationContext.getBean("graph");
	}
}
