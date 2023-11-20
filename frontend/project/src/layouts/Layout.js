import Header from './Header';
import './css/Layout.module.min.css';
import Footer from './Footer';
const Layout = ({ children }) => {
  return (
    <div className="wrapper">
      <Header />
      <main>{children}</main>
      <Footer />
    </div>
  );
};

export default Layout;
