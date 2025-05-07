import IMAGES from "../assets/images/images";
import axios from "axios";
import Cookies from "js-cookie";
import { Box, Typography } from "@mui/material";

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
  console.log("url in post axios call is", url, "body is", body);
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
  unit === "mi"
    ? (distance = (value * 1609.34).toFixed(2))
    : unit === "yd"
    ? (distance = (value * 0.9144).toFixed(2)) //yards
    : (distance = value); //meters
  return distance;
};

export const fromMeters = (meters, toUnit) => {
  let distance;
  toUnit === "mi"
    ? (distance = +(meters / 1609.34).toFixed(2))
    : toUnit === "yd"
    ? (distance = +(meters / 0.9144).toFixed(2))
    : (distance = meters);
  return distance;
};

export const displayCardioText = (exercise) => {
  const cardioSet = exercise?.sets?.[0];
  if (!cardioSet) return null;

  const { distance, distanceUnit, duration } = cardioSet;
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
    displayDuration = `${distance} ${distanceUnit}`;
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
  console.log("combinedSets are", combinedSets);
  return combinedSets;
};
