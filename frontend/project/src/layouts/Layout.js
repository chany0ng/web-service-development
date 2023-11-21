import Header from './Header';
import styles from './css/layout.module.min.css';
import Footer from './Footer';
const Layout = ({ children }) => {
  console.log(styles.wrapper);
  return (
    <div className={styles.wrapper}>
      <Header />
      <main className={styles.main}>{children}</main>
      <Footer />
    </div>
  );
};

export default Layout;
