package world.pointsofinterest.services.interfaces;


import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommonService<REQ, RES, ID> {

    RES findById(ID id);

    RES save(REQ object);

    RES update(ID id, REQ object);

    void deleteById(ID id);
}
