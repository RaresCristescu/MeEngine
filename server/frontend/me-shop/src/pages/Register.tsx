import { useRef, useState, useEffect } from "react";
import HttpClient from "../lib/HttpClient.tsx";


const USER_REGEX = /^[a-zA-Z][a-zA-Z0-9-_]{3,23}$/;
const PWD_REGEX = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%]).{8,24}$/;

const Register = () => {
    const userRef = useRef<any>();
    const errRef = useRef<any>();

    const [user, setUser] = useState('');
    const [validName, setValidName] = useState(false);
    const [userFocus, setUserFocus] = useState(false);

    const [email, setEmail] = useState('');
    const [emailFocus, setEmailFocus] = useState(false);

    const [pwd, setPwd] = useState('');
    const [validPwd, setValidPwd] = useState(false);
    const [pwdFocus, setPwdFocus] = useState(false);

    const [matchPwd, setMatchPwd] = useState('');
    const [validMatch, setValidMatch] = useState(false);
    const [matchFocus, setMatchFocus] = useState(false);

    const [errMsg, setErrMsg] = useState('');

    useEffect(() => {
        userRef.current.focus();
    }, [])

    useEffect(() => {
        const result = USER_REGEX.test(user);
        console.log(result);
        console.log(user);
        setValidName(result);
    }, [user])

    useEffect(() => {
        const result = PWD_REGEX.test(pwd);
        console.log(result);
        console.log(pwd);
        setValidPwd(result);
        const match = pwd === matchPwd;
        setValidMatch(match);
    }, [pwd, matchPwd])

    useEffect(() => {
        setErrMsg('');
    }, [user, pwd, matchPwd])



    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        //if button enabled with js hack
        const v1 = USER_REGEX.test(user);
        const v2 = PWD_REGEX.test(pwd);
        if(!v1 || !v2){
            setErrMsg("Invalid Entry");
            return;
        }

        try {
            await HttpClient.post("/user/register", {
            login:user,
            password:pwd,
            email:email
            });

            alert("User registered successfully!");
            console.log(user, pwd, email);
        } catch (error) {
            console.error(error);
            alert("Registration failed");
        }
  }

    return (
        <section>
            <p ref={errRef} className={errMsg ? "errmsg" : "offscreen"} aria-live="assertive">{errMsg}</p>
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="username">
                    Username:
                    <span className={validName ? "valid" : "hide"}>
                        <i className="bi bi-check"></i>
                    </span>
                    <span className={validName || !user ? "hide" : "invalid"}>
                        <i className="bi bi-x-octagon"></i>
                    </span>
                </label>
                <input
                    type="text"
                    id="username"
                    ref={userRef}
                    autoComplete="off"
                    onChange={(e) => setUser(e.target.value)}
                    required
                    aria-invalid={validName ? "false" : "true"}
                    aria-describedby="uidnote"
                    onFocus={() => setUserFocus(true)}
                    onBlur={() => setUserFocus(false)}
                />
                <p id="uidnote" className={userFocus && user && !validName ? "instructions" : "offscreen"}>
                    <i className="bi bi-info-circle"></i>
                    4 to 24 characters.<br />
                    Must begin with a letter.<br />
                    Letters, numbers, underscores, hyphens, allowed.
                </p>

                <label htmlFor="email">
                    Email:
                </label>
                <input
                    type="text"
                    id="email"
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    aria-describedby="uidnote"
                    onFocus={() => setEmailFocus(true)}
                    onBlur={() => setEmailFocus(false)}
                />
                <p id="uidnote" className={emailFocus ? "instructions" : "offscreen"}>
                    <i className="bi bi-info-circle"></i>
                    Complete email address.
                </p>

                <label htmlFor="password">
                    Password:
                    <span className={validPwd ? "valid" : "hide"}>
                        <i className="bi bi-check"></i>
                    </span>
                    <span className={validPwd || !pwd ? "hide" : "invalid"}>
                        <i className="bi bi-x-octagon"></i>
                    </span>
                </label>
                <input
                    type="password"
                    id="password"
                    onChange={(e) => setPwd(e.target.value)}
                    required
                    aria-invalid={validPwd ? "false" : "true"}
                    aria-describedby="pwdnote"
                    onFocus={() => setPwdFocus(true)}
                    onBlur={() => setPwdFocus(false)}
                />
                <p id="pwdnote" className={pwdFocus && !validPwd ? "instructions" : "offscreen"}>
                    <i className="bi bi-info-circle"></i>
                    8 to 24 characters.<br />
                    Must include uppercase and lowercase letters, a number and a special character.<br />
                    Allowed special characters:
                    <span aria-label="exclamation mark">!</span>
                    <span aria-label="at symbol">@</span>
                    <span aria-label="hashtag">#</span>
                    <span aria-label="dollar sign">$</span>
                    <span aria-label="precent">%</span>
                </p>

                <label htmlFor="confirm_pwd">
                    Confirm Password:
                    <span className={validMatch && matchPwd ? "valid" : "hide"}>
                        <i className="bi bi-check"></i>
                    </span>
                    <span className={validMatch || !matchPwd ? "hide" : "invalid"}>
                        <i className="bi bi-x-octagon"></i>
                    </span>
                </label>
                <input
                    type="password"
                    id="confirm_pwd"
                    onChange={(e) => setMatchPwd(e.target.value)}
                    required
                    aria-invalid={validMatch ? "false" : "true"}
                    aria-describedby="confirmnote"
                    onFocus={() => setMatchFocus(true)}
                    onBlur={() => setMatchFocus(false)}
                />
                <p id="confirmnote" className={matchFocus && !validMatch ? "instructions" : "offscreen"}>
                    <i className="bi bi-info-circle"></i>
                    Must match the first password input field.
                </p>

                <button disabled={!validName || !validPwd || !validMatch ? true : false}
                >Sign Up</button>
            </form>
            <p>
                Already registered? <br/>
                {/* put router link here*/}
                <a href="#">Sign In</a>
            </p>
        </section>
    )
}

export default Register