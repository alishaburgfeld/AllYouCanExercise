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
  // console.log("url in get axios call is", url);
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

export const postAxiosCall = async (url, body) => {
  // console.log("url in post axios call is", url);
  try {
    const csrfToken = Cookies.get("XSRF-TOKEN");
    const response = await axios.post(url, body, {
      headers: {
        "X-XSRF-TOKEN": csrfToken,
      },
      withCredentials: true,
    });

    if (response.data) {
      return { success: true, data: response.data };
    } else {
      console.log("no response.data in axios post call");
      return { success: false, error: "No data" };
    }
  } catch (error) {
    console.log("Axios Call Failed", error);

    if (error.response && error.response.status === 401) {
      return { success: false, error: "Unauthorized" };
    }

    return {
      success: false,
      error: error.message || "Unknown error",
    };
  }
};

export const formatExerciseDurationIntoMinutesAndSeconds = (
  durationInSeconds,
) => {
  const minutes = Math.floor(durationInSeconds / 60);
  const seconds = durationInSeconds % 60;
  return `${minutes}:${seconds < 10 ? "0" + seconds : seconds}`;
};

export const metersToMiles = (meters) => {
  const milesPerMeter = 0.000621371;
  return meters * milesPerMeter;
};

export const metersToYards = (meters) => {
  const yardsPerMeter = 1.09361;
  return meters * yardsPerMeter;
};
