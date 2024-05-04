///**
// * 어플리케이션이 시작될 때 사용자 정보 insert
// */
//package proj.petbuddy;
//
//import jakarta.annotation.PostConstruct;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//import proj.petbuddy.domain.board.Board;
//import proj.petbuddy.domain.category.Category;
//import proj.petbuddy.domain.item.Item;
//import proj.petbuddy.domain.map.Store;
//import proj.petbuddy.domain.mypage.*;
//import proj.petbuddy.repository.board.BoardRepository;
//import proj.petbuddy.repository.board.CategoryRepository;
//import proj.petbuddy.repository.item.ItemRepository;
//import proj.petbuddy.repository.member.MemberRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class testDB {
//
//
//
//    private final TestService testService;
//
//
//    @PostConstruct
//    public void test() {
//        testService.boardDB();
//        testService.createAdminUser();
//        testService.testCategoryHierarchy();
//        testService.testCategoryHierarchy2();
//        testService.testItem();
//        testService.testStore();
//    }
//
//
//    @Component
//    @Transactional
//    @RequiredArgsConstructor
//    private static class TestService {
//
//        private final BoardRepository boardRepository;
//        private final PasswordEncoder passwordEncoder;
//        private final MemberRepository memberRepository;
//        private final CategoryRepository categoryRepository;
//        private final ItemRepository itemRepository;
//
//
//        /**
//         * 관리자 등록
//         **/
//        @Transactional
//        public void createAdminUser() {
//            Member member = new Member();
//            Address address = new Address("서울특별시 용산구", "512-7");
//            member.setId("admin11");
//            member.setName("admin");
//            member.setPhoneNumber("01012345678");
//            member.setBirth("1991.03.12");
//            member.setAddress(address);
//            member.setEventReceipt(EventReceipt.RECEIVE);
//            member.setPassword(passwordEncoder.encode("admin11!!"));
//            member.setPersonalInformation(PersonalInformation.DISAGREE);
//            member.setRole(Role.ADMIN);
//            memberRepository.save(member);
//        }
//
//        /**
//         * 게시판 임시 등록
//         **/
//        @Transactional
//        public void boardDB() {
//            List<Board> boardList = boardRepository.findAll();
//            if (boardList.size() == 0) {
//                for (int i = 0; i < 172; i++) {
//                    Board board = Board.builder()
//                            .title("테스트제목" + i)
//                            .content("테스트내용" + i)
//                            .build();
//                    boardRepository.save(board);
//                }
//            }
//        }
//
//
//        /**
//         * 카테 임시 등록
//         **/
//        @Transactional
//        public void testCategoryHierarchy() {
//            // 대분류(여성의류, 남성의류, 라이프)
//            Category category = new Category("women", 0L);
//            Category category2 = new Category("men", 0L);
//            Category category3 = new Category("life", 0L);
//            categoryRepository.save(category);
//            categoryRepository.save(category2);
//            categoryRepository.save(category3);
//
//        }
//
//        @Transactional
//        public void testCategoryHierarchy2() {
//            // 중분류(티셔츠, 아우터~)
//            Category category4 = new Category("tshirt", 1L, 1L);
//            categoryRepository.save(category4);
//            Category category5 = new Category("denim", 1L, 1L);
//            categoryRepository.save(category5);
//            Category category6 = new Category("onepiece", 1L, 1L);
//            categoryRepository.save(category6);
//            Category category7 = new Category("outer", 1L, 1L);
//            categoryRepository.save(category7);
//            Category category8 = new Category("pants", 1L, 1L);
//            categoryRepository.save(category8);
//            Category category9 = new Category("shirt", 1L, 1L);
//            categoryRepository.save(category9);
//            Category category10 = new Category("skirt", 1L, 1L);
//            categoryRepository.save(category10);
//            Category category11 = new Category("sleeve", 1L, 1L);
//            categoryRepository.save(category11);
//
//            Category category12 = new Category("tshirt", 2L, 1L);
//            categoryRepository.save(category12);
//            Category category13 = new Category("denim", 2L, 1L);
//            categoryRepository.save(category13);
//            Category category14 = new Category("outer", 2L, 1L);
//            categoryRepository.save(category14);
//            Category category15 = new Category("pants", 2L, 1L);
//            categoryRepository.save(category15);
//            Category category16 = new Category("shirt", 2L, 1L);
//            categoryRepository.save(category16);
//            Category category17 = new Category("sleeve", 2L, 1L);
//            categoryRepository.save(category17);
//
//        }
//
//        /**
//         * 상품 임시 등록
//         **/
//        @Transactional
//        public void testItem() {
//            // test 상품1
//            Item item = new Item();
//            Category category = new Category();
//            category.setId(11L);
//
//            item.setCategory(category);
//            item.setBrand("BMUET(TE)");
//            item.setName("[ROMANTIC EDITION] PUFF SLEEVE BLOUSE_WHITE");
//            item.setPrice(325000);
//            item.setDiscount(9);
//            item.setImgStr("https://product-image.wconcept.co.kr/productimg/image/img9/42/305776542_FB98216.jpg?RS=374");
//            item.setHeart(2300);
//            item.setCount(999L);
//            itemRepository.save(item);
//
//
//            // test 상품2
//            Item item2 = new Item();
//            Category category2 = new Category();
//            category2.setId(11L);
//
//            item2.setCategory(category2);
//            item2.setBrand("BMUET(TE)");
//            item2.setName("[ROMANTIC EDITION] PLEATED CUTTING LINE DETAIL VOLUME SKIRT_BLACK");
//            item2.setPrice(425000);
//            item2.setDiscount(7);
//            item2.setImgStr("https://product-image.wconcept.co.kr/productimg/image/img9/89/305776589_OU54237.jpg?RS=374");
//            item2.setHeart(5627);
//            item2.setCount(999L);
//            itemRepository.save(item2);
//
//
//            // test 상품3
//            Item item3 = new Item();
//            Category category3 = new Category();
//            category3.setId(11L);
//
//            item3.setCategory(category3);
//            item3.setBrand("EPT");
//            item3.setName("AUTUMN NIBBLE T-SHIRT (NAVY)");
//            item3.setPrice(69520);
//            item3.setDiscount(12);
//            item3.setImgStr("https://product-image.wconcept.co.kr/productimg/image/img9/25/305588425_KH41629.jpg?RS=374");
//            item3.setHeart(4210);
//            item3.setCount(999L);
//            itemRepository.save(item3);
//        }
//
//
//        /**
//         * 지점 임시 등록
//         **/
//        @PersistenceContext
//        private EntityManager entityManager;
//
//        @Transactional
//        public void testStore() {
//            // 지점정보 (지점명, 주소, 전화번호~)
//            Store store1 = new Store("현대백화점 압구정본점", "강남구 압구정로 165 현대백화점", "+ 82 (0)2 514 8855", "서울특별시, 대한민국", "127.027458", "37.5273756", "06001","월요일 수신: 목요일 - 10:30 수신: 20:00", "금요일 수신: 일요일 - 10:30 수신: 20:30");
//            entityManager.persist(store1);
//
//            Store store2 = new Store("롯데백화점 월드타워점", "송파구 올림픽로 300 롯데백화점 월드타워점", "+ 82 (0)2 524 1745", "서울특별시, 대한민국", "127.1025625", "37.5125702","05551", "월요일 수신: 목요일 - 10:00 수신: 20:00", "금요일 수신: 일요일 - 10:00 수신: 20:30");
//            entityManager.persist(store2);
//
//            Store store3 = new Store("롯데면세점 명동 본점", "중구 을지로 30 롯데백화점 본점", "+ 82 (0)2 743 6754", "서울특별시, 대한민국", "126.9816208", "37.5654272", "04533", "월요일 수신: 목요일 - 10:00 수신: 20:00", "금요일 수신: 일요일 - 10:30 수신: 20:00");
//            entityManager.persist(store3);
//
//            Store store4 = new Store("신세계백화점 강남점", "서초구 신반포로 176 신세계백화점 강남점", "+ 82 (0)2 3469 1243", "서울특별시, 대한민국", "127.0047519", "37.5038952", "6546", "월요일 수신: 목요일 - 10:00 수신: 20:00", "금요일 수신: 일요일 - 10:30 수신: 20:30");
//            entityManager.persist(store4);
//
//            Store store5 = new Store("신세계면세점 명동점", "중구 퇴계로 77 신세계백화점 본점", "+82 (0)2 6290 4369", "서울특별시, 대한민국", "126.980743", "37.5603276", "04530", "월요일 수신: 목요일 - 10:00 수신: 20:00","금요일 수신: 일요일 - 10:30 수신: 20:30");
//            entityManager.persist(store5);
//
//            Store store6 = new Store("신세계면세점 인천공항 2터미널점", "신세계면세점 인천공항 2터미널점", "+ 82 (0)32 743 4142", "인천광역시, 대한민국", "126.4276993", "37.4583507", "월요일 수신: 목요일 - 10:30 수신: 20:00", "22382","금요일 수신: 일요일 - 10:30 수신: 20:00");
//            entityManager.persist(store6);
//
//            Store store7 = new Store("신세계백화점 대구점", "동구 동부로 149 신세계 백화점 대구점", "+ 82 (0)53 511 6503", "대구광역시, 대한민국", "128.6291671", "35.8777043", "41229", "월요일 수신: 목요일 - 10:00 수신: 19:30","금요일 수신: 일요일 - 10:00 수신: 20:00");
//            entityManager.persist(store7);
//
//            Store store8 = new Store("현대백화점 무역센터점", "강남구 테헤란로 517 현대백화점 무역센터점", "'+ 82 (0)2 3467 7892", "서울특별시, 대한민국", "127.0598879", "37.5086329", "06164", "월요일 수신: 목요일 - 10:00 수신: 20:00","금요일 수신: 일요일 - 10:30 수신: 20:30");
//            entityManager.persist(store8);
//
//            Store store9 = new Store("현대백화점 판교점", "성남시 분당구 판교역로146번길 20", "+82 (0)31 5162 1137", "경기도, 대한민국", "127.1120875", "37.3927946", "13529", "월요일 수신: 목요일 - 10:00 수신: 20:00","금요일 수신: 일요일 - 10:30 수신: 20:30");
//            entityManager.persist(store9);
//
//            Store store10 = new Store("신라면세점 서울점", "중구 동호로 249 신라면세점", "+ 82 (0)2 2131 3644", "서울특별시, 대한민국", "127.0051533", "37.5558806", "04605", "월요일 수신: 목요일 - 10:00 수신: 20:00","금요일 수신: 일요일 - 10:00 수신: 20:30");
//            entityManager.persist(store10);
//
//            Store store11 = new Store("롯데면세점 부산점", "부산진구 가야대로 772 롯데백화점 부산점", "+ 82 (0)51 810 3431", "서울특별시, 대한민국", "129.0554875", "35.1568154", "47285", "월요일 수신: 목요일 - 10:00 수신: 20:00","금요일 수신: 일요일 - 10:30 수신: 20:00");
//            entityManager.persist(store11);
//
//            Store store12 = new Store("신세계백화점 센텀시티점", "해운대구 센텀남대로 35 신세계백화점 센텀시티점", "+ 82 (0)51 795 1311", "서울특별시, 대한민국", "129.1295233", "35.1688179", "48058", "월요일 수신: 목요일 - 10:30 수신: 20:00", "금요일 수신: 일요일 - 10:30 수신: 20:30");
//            entityManager.persist(store12);
//
//            Store store13 = new Store("롯데면세점 월드타워점", "송파구 올림픽로 300 롯데월드타워몰 에비뉴엘동", "+82 (0)2 4213 3640", "서울특별시, 대한민국", "127.1025625", "37.5125702", "05551", "월요일 수신: 목요일 - 10:00 수신: 20:00", "금요일 수신: 일요일 - 10:30 수신: 20:30");
//            entityManager.persist(store13);
//
//        }
//
//
//    }
//}
//
//
//
//
