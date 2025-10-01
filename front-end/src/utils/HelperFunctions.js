import IMAGES from "./images";
import axios from "axios";
import Cookies from "js-cookie";
import { Box, Typography } from "@mui/material";

export const getImageSource = (name) => {
  return IMAGES[name] || "/images/noexerciseimage.png";
};

// export const getAxiosCall = async (url) => {
//   // console.log("url in get axios call is", url);
//   try {
//     const csrfToken = Cookies.get("XSRF-TOKEN");
//     const response = await axios.get(url, {
//       headers: {
//         "X-XSRF-TOKEN": csrfToken,
//       },
//       withCredentials: true,
//     });
//     if (response.data) {
//       return response.data;
//     } else {
//       console.log(
//         "no response.data in axios get call, response is",
//         response,
//       );
//     }
//   } catch (error) {
//     console.log("Axios Call Failed", error);
//   }
// };

export const getAxiosCall = async (url) => {
  try {
    const csrfToken = Cookies.get("XSRF-TOKEN");
    const response = await axios.get(url, {
      headers: {
        "X-XSRF-TOKEN": csrfToken,
      },
      withCredentials: true,
    });

    console.log("✅ Raw Axios response:", response);
    // console.log("✅ Raw response.data type:", typeof response.data);
    // console.log("✅ Raw response.data value:", response.data);

    return response.data;
  } catch (error) {
    console.log("❌ Axios Call Failed", error);
  }
};

export const postAxiosCall = async (url, body) => {
  // console.log("url in post axios call is", url, "body is", body);
  try {
    const csrfToken = Cookies.get("XSRF-TOKEN");
    const response = await axios.post(url, body, {
      headers: {
        "X-XSRF-TOKEN": csrfToken,
      },
      withCredentials: true,
    });

    if (response.status === 409) {
      return { success: false, error: response.data };
    }

    if (response.data || response.success) {
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

export const toTitleCase = (str) => {
  return str.replace(/\w\S*/g, function (txt) {
    return (
      txt.charAt(0).toUpperCase() + txt.substring(1).toLowerCase()
    );
  });
};

export const convertToJavaTime = (dateTime) => {
  const timeFormattedForJava = dateTime
    .toLocaleString("en-US", {
      year: "numeric",
      month: "2-digit",
      day: "2-digit",
      hour: "2-digit",
      minute: "2-digit",
      second: "2-digit",
      hour12: false, // 24-hour format (matches Java LocalDateTime format)
    })
    .replace(",", "") // Remove the comma for Java compatibility
    .replace(
      /(\d{2})\/(\d{2})\/(\d{4}) (\d{2}):(\d{2}):(\d{2})/,
      "$3-$1-$2T$4:$5:$6",
    );
  return timeFormattedForJava;
};

export const convertJavaLocalDateTimeToUserLocalTime = (
  javaLocalDateTimeString,
) => {
  // console.log("javalocaltime is", javaLocalDateTimeString);
  const dateObject = new Date(javaLocalDateTimeString);
  const formatter = new Intl.DateTimeFormat("en-US", {
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "numeric",
    minute: "numeric",
  });
  const formattedDate = formatter.format(dateObject); // e.g., "Aug 29, 2025, 5:02 PM"
  return formattedDate;
};

export const formatExerciseDurationIntoMinutesAndSeconds = (
  durationInSeconds,
) => {
  const hours = Math.floor(durationInSeconds / 3600);
  const minutes = Math.floor((durationInSeconds % 3600) / 60);
  const seconds = durationInSeconds % 60;

  const paddedMinutes = minutes.toString().padStart(2, "0");
  const paddedSeconds = seconds.toString().padStart(2, "0");

  return `${hours}:${paddedMinutes}:${paddedSeconds}`;
};

export const convertFromSeconds = (totalSeconds) => {
  const hours = Math.floor(totalSeconds / 3600);
  const minutes = Math.floor((totalSeconds % 3600) / 60);
  const seconds = totalSeconds % 60;
  return { hours, minutes, seconds };
};

export const convertToSeconds = (duration) => {
  const { hours, minutes, seconds } = duration;
  return hours * 3600 + minutes * 60 + seconds;
};

export const toMeters = (value, unit) => {
  let distance;
  unit === "MILES"
    ? (distance = (value * 1609.34).toFixed(2))
    : unit === "YARDS"
    ? (distance = (value * 0.9144).toFixed(2)) //yards
    : (distance = value); //meters
  return distance;
};

export const fromMeters = (meters, toUnit) => {
  let distance;
  toUnit === "MILES"
    ? (distance = +(meters / 1609.34).toFixed(2))
    : toUnit === "YARDS"
    ? (distance = +(meters / 0.9144).toFixed(2))
    : (distance = meters);
  return distance;
};

export const displayCardioText = (exercise) => {
  const cardioSet = exercise?.sets?.[0];
  if (!cardioSet) return null;

  const { distance, distanceMeasurement, duration } = cardioSet;
  const { hours, minutes, seconds } = duration || {};

  let cardioValues = {};
  let displayDistance = "";
  let displayDuration = "";
  if (hours || minutes || seconds) {
    displayDistance = `H:${hours} M:${minutes} S:${
      seconds < 10 ? "0" + seconds : seconds
    }`;
  }
  if (distance) {
    displayDuration = `${distance} ${distanceMeasurement}`;
  }
  cardioValues["displayDistance"] = displayDistance;
  cardioValues["displayDuration"] = displayDuration;
  return cardioValues;
  // console.log("1) in display cardio sets, displaydistance is", displayDistance, "displayduration is", displayDuration)
};

export const displayReps = (exercise) => {
  const sets = exercise.sets;
  // console.log('in display rep sets, sets are', sets)
  if (!sets || sets.length === 0) return "No data";

  let combinedSets = [];
  let count = 1;

  for (let i = 1; i <= sets.length; i++) {
    const current = sets[i];
    const prev = sets[i - 1];

    if (
      current &&
      prev &&
      current.reps === prev.reps &&
      current.weight === prev.weight
    ) {
      count++;
    } else {
      combinedSets.push(`${count}x${prev.reps}:${prev.weight} lbs`);
      count = 1;
    }
  }
  // console.log("combinedSets are", combinedSets);
  return combinedSets;
};
