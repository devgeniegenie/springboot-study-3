package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용
 */
public class MemberRepository {

    //static으로 생성하여 MemberRepository가 new로 계속 생성되어도 store, sequence는 하나만 사용되도록
    //(애플리케이션 전체에서 공유할 객체 혹은 변수를 만들때 static 사용)
    //sington이라 static 없어도 하나인게 보장되긴 함
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    //sington
    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance() {
        return instance;
    }

    private MemberRepository() {
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        //새로운 ArrayList에 넣어서 줌. store보호
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
