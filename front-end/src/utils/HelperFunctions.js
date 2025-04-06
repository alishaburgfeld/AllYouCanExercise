import IMAGES from "../assets/images/images";
import axios from "axios";
import Cookies from "js-cookie";

export const getImageSource = (name) => {
  let source;
  if (IMAGES[name]) {
    source = IMAGES[name];
  } else {
    let noImageString = "../assets/images/noexerciseimage.png";
    source = new URL(noImageString, import.meta.url).href;
  }
  return source;
};

export const getAxiosCall = async (url) => {
  console.log("url in get axios call is", url);
  try {
    const csrfToken = Cookies.get("XSRF-TOKEN");
    const response = await axios.get(url, {
      headers: {
        "X-XSRF-TOKEN": csrfToken,
      },
      withCredentials: true,
    });
    if (response.data) {
      return response.data;
    } else {
      console.log("no response.data in axios get call");
    }
  } catch (error) {
    console.log("Axios Call Failed", error);
  }
};
