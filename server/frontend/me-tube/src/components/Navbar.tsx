import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Navbar: React.FC = () => {
  const [open, setOpen] = useState(false);
  const navigate = useNavigate();

  const logout = () => {
    // Remove token (cookie example)
    document.cookie = "token=; Max-Age=0; path=/;";
    
    navigate("/");
  };

  return (
    <nav style={styles.nav}>
      <div style={styles.right}>
        <div style={styles.iconWrapper} onClick={() => setOpen(!open)}>
          <i className="bi bi-person-circle" style={styles.icon}></i>
        </div>

        {open && (
          <div style={styles.dropdown}>
            <button onClick={logout} style={styles.logoutBtn}>
              Logout
            </button>
          </div>
        )}
      </div>
    </nav>
  );
};

const styles = {
  nav: {
    height: "60px",
    display: "flex",
    justifyContent: "flex-end",
    alignItems: "center",
    padding: "0 20px",
    borderBottom: "1px solid #ddd",
  },
  right: {
    position: "relative" as const,
  },
  iconWrapper: {
    cursor: "pointer",
  },
  icon: {
    fontSize: "28px",
  },
  dropdown: {
    position: "absolute" as const,
    top: "40px",
    right: 0,
    background: "#fff",
    border: "1px solid #ddd",
    borderRadius: "4px",
    padding: "8px",
  },
  logoutBtn: {
    background: "none",
    border: "none",
    cursor: "pointer",
    color: "red",
  },
};

export default Navbar;
