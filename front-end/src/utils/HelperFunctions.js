import IMAGES from "../assets/images/images";

export const getImageSource = (name) => {
  let source;
  if (IMAGES[name]) {
    console.log(name, "name was found in images");
    source = IMAGES[name];
  } else {
    let noImageString = "../assets/images/noexerciseimage.png";
    source = new URL(noImageString, import.meta.url).href;
  }
  return source;
};
