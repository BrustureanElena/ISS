package persistance;

import firma.AgentVanzari;

public interface AgentRepository  extends RepositoryCRUD<Integer, AgentVanzari>{

    AgentVanzari findAgentByUsernameParola(String username,String parola);

}
