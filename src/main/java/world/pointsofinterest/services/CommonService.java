package world.pointsofinterest.services;

import java.util.List;


public interface CommonService<REQ, RES, ID> {

    List<RES> findAll();

    RES findById(ID id);

    RES save(REQ object);

    RES update(ID id, REQ object);

    void deleteById(ID id);
}
