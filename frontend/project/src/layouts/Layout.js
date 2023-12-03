import Header from './Header';
import styles from './Layout.module.scss';
import Footer from './Footer';

const sections = [
  { title: '메인 화면', url: '/user/main' },
  { title: '내 정보 관리', url: '/user/info/edit' },
  { title: '대여소 조회', url: '#' },
  { title: '이용권 구매', url: '/user/tickets/purchase' },
  { title: '공지/문의', url: '/user/notice/noticeList/1' }
];

const Layout = ({ children }) => {
  return (
    <div className={styles.wrapper}>
      <Header title="따릉이 프로젝트" sections={sections} />
      <main className={styles.main}>{children}</main>
      <Footer />
    </div>
  );
};

export default Layout;
