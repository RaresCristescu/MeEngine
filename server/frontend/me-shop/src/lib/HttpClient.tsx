import axios from "axios";

// Helper to get a cookie by name
function getCookie(name: string): string | null {
  const matches = document.cookie.match(
    new RegExp(
      "(?:^|; )" + name.replace(/([.$?*|{}()[\]\\/+^])/g, "\\$1") + "=([^;]*)"
    )
  );
  return matches ? decodeURIComponent(matches[1]) : null;
}

// Create Axios instance
const api = axios.create({
  baseURL: "http://localhost:8080/api",
  headers: {
    "Content-Type": "application/json",
  },
});

// Interceptor to add Authorization if cookie exists
api.interceptors.request.use((config) => {
  const token = getCookie("token");
  if (token) {
    // Type assertion to satisfy Axios v1 typing
    if (!config.headers) config.headers = {} as any;
    (config.headers as any)["Authorization"] = `${token}`;
  }
  return config;
});

export default api;
