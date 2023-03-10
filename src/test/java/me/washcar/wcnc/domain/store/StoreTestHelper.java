package me.washcar.wcnc.domain.store;

import static me.washcar.wcnc.domain.member.MemberRole.*;

import org.springframework.stereotype.Component;

import me.washcar.wcnc.domain.member.MemberAuthenticationType;
import me.washcar.wcnc.domain.member.MemberStatus;
import me.washcar.wcnc.domain.member.entity.Member;
import me.washcar.wcnc.domain.store.dto.request.StoreRequestDto;
import me.washcar.wcnc.domain.store.entity.Location;
import me.washcar.wcnc.domain.store.entity.Store;

@Component
public class StoreTestHelper {

	private Member makeStaticOwner() {
		return Member.builder()
			.loginId("00WN")
			.nickname("StaticOwner-00AA")
			.memberRole(ROLE_OWNER)
			.memberStatus(MemberStatus.ACTIVE)
			.memberAuthenticationType(MemberAuthenticationType.PASSWORD)
			.loginPassword("StaticOwner-PASSWORD")
			.telephone("01020003000")
			.build();
	}

	private Store makeStaticStore() {
		Location location = new Location(30.001, 60.001, "ADD", "DETAIL", "WAY-TO");
		Store store = Store.builder()
			.slug("testslug")
			.location(location)
			.name("testName")
			.tel("010-2341-2312")
			.description("testDescription")
			.previewImage("https://testimage.net/storeImage")
			.build();
		store.assignOwner(makeStaticOwner());
		return store;
	}

	public Store makeStaticPendingStore() {
		Store store = makeStaticStore();
		store.updateStatus(StoreStatus.PENDING);
		return store;
	}

	public Store makeStaticRejectedStore() {
		Store store = makeStaticStore();
		store.updateStatus(StoreStatus.REJECTED);
		return store;
	}

	public Store makeStaticRunningStore() {
		Store store = makeStaticStore();
		store.updateStatus(StoreStatus.RUNNING);
		return store;
	}

	public Store makeStaticClosedStore() {
		Store store = makeStaticStore();
		store.updateStatus(StoreStatus.CLOSED);
		return store;
	}

	public StoreRequestDto makeStaticStoreRequestDto() {
		Location location = new Location(35.001, 65.001, "QDD", "DETAIL", "WAY-TO");
		return new StoreRequestDto("testslug", location, "testname2", "010-2341-2312", "testDescription",
			"https://testimage.net/storeImage");
	}
}
