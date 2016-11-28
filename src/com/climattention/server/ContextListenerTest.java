package com.climattention.server;

import static org.junit.Assert.*;

import org.junit.Test;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.climattention.shared.Datapoint;

public class ContextListenerTest {

	@Test
	public void testContextInitialized() {
		// claudio kannst du das hier anschauen?
		ContextListener listen = new ContextListener();
		ServletContextEvent event = new ServletContextEvent(null);
		// nulll als parameter is nicht optimal, aber was für parameter sollten das sein?
		
		listen.contextInitialized(event);
		
		
	}

}
