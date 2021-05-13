package persistance;

import firma.Produs;

import java.util.List;

public interface ProdusRepository extends RepositoryCRUD{
    List<Produs> getToateProduseleVandute();
}
