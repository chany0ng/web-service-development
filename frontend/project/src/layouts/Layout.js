import Header from './Header';
import styles from './Layout.module.scss';
import Footer from './Footer';

const sections = [
  { title: '메인 화면', url: '/user/main' },
  { title: '내 정보 관리', url: '/user/info/edit' },
  { title: '대여소 조회', url: '/user/map' },
  { title: '이용권 구매', url: '/user/tickets/purchase' },
  { title: '공지/문의', url: '/user/notice/noticeList/1' }
];

const sections2 = [
  { title: '메인 화면', url: '/admin/main' },
  { title: '회원 관리', url: '/admin/info/list/1' },
  { title: '따릉이 관리', url: '/admin/manage/bike/1' },
  { title: '공지/신고 관리', url: '/admin/notice/list/1' }
];

const Layout = ({ children, admin }) => {
  return (
    <div className={styles.wrapper}>
      <Header title="따릉이 프로젝트" sections={admin ? sections2 : sections} />
      <main className={styles.main}>{children}</main>
      <Footer />
    </div>
  );
};

export default Layout;
