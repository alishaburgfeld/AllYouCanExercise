import { getImageSource } from "../utils/HelperFunctions";

describe("getImageSource", () => {
  test("happy path", () => {
    const name = "Dumbbell Overhead Press";

    const response = getImageSource(name);

    expect(response).toEqual(
      "file:///Users/alisha.burgfeld/Documents/AllYouCanExercise/front-end/src/assets/images/SHOULDERS/Dumbbell%20Overhead%20Press.png",
    );
  });
  test("unhappy path", () => {
    const name = "Raise Elliot to the Roof";

    const response = getImageSource(name);

    expect(response).toEqual(
      "file:///Users/alisha.burgfeld/Documents/AllYouCanExercise/front-end/src/assets/images/noexerciseimage.png",
    );
  });
});
