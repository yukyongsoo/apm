package agent.agentC.net;

import yuk.parser.DataParser;
import yuk.parser.inter.CommandInterface;

public class AgentDataParser extends DataParser{
	public AgentDataParser(CommandInterface agent, CommandInterface collector, CommandInterface server,
			CommandInterface console, CommandInterface etc) {
		super(agent, collector, server, console, etc);
	}
}
