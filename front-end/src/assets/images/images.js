const IMAGES = {
  "Abduction Machine": new URL(
    "./ABDUCTORS/Abduction Machine.png",
    import.meta.url,
  ).href,
  "Around the World": new URL(
    "./SHOULDERS/Around the World.png",
    import.meta.url,
  ).href,
  "Dumbbell Overhead Press": new URL(
    "./SHOULDERS/Dumbbell Overhead Press.png",
    import.meta.url,
  ).href,
  "Overhead Press Single Arm": new URL(
    "./SHOULDERS/Overhead Press Single Arm.png",
    import.meta.url,
  ).href,
  "Barbell Overhead Press": new URL(
    "./SHOULDERS/Barbell Overhead Press.png",
    import.meta.url,
  ).href,
  "Barbell Behind the Neck Press": new URL(
    "./SHOULDERS/Barbell Behind the Neck Press.png",
    import.meta.url,
  ).href,
  "Barbell Upright Row": new URL(
    "./SHOULDERS/Barbell Upright Row.png",
    import.meta.url,
  ).href,
  "EZ Bar Upright Row": new URL(
    "./SHOULDERS/EZ Bar Upright Row.png",
    import.meta.url,
  ).href,
  "Cable Upright Row": new URL(
    "./SHOULDERS/Cable Upright Row.png",
    import.meta.url,
  ).href,
  "Dumbbell Upright Row": new URL(
    "./SHOULDERS/Dumbbell Upright Row.png",
    import.meta.url,
  ).href,
  "Dumbbell Lateral Raise": new URL(
    "./SHOULDERS/Dumbbell Lateral Raise.png",
    import.meta.url,
  ).href,
  "Barbell Front Raise": new URL(
    "./SHOULDERS/Barbell Front Raise.png",
    import.meta.url,
  ).href,
  "Dumbbell Front Raise": new URL(
    "./SHOULDERS/Dumbbell Front Raise.png",
    import.meta.url,
  ).href,
  "Dumbbell Bent Over Reverse Fly": new URL(
    "./SHOULDERS/Dumbbell Bent Over Reverse Fly.png",
    import.meta.url,
  ).href,
  "Dumbbell Incline Prone Reverse Fly": new URL(
    "./SHOULDERS/Dumbbell Incline Prone Reverse Fly.png",
    import.meta.url,
  ).href,
  "Cable Face Pull": new URL(
    "./SHOULDERS/Cable Face Pull.png",
    import.meta.url,
  ).href,
  "Barbell Bench Press": new URL(
    "./CHEST/Barbell Bench Press.png",
    import.meta.url,
  ).href,
  "Barbell Bench Press Close Grip": new URL(
    "./CHEST/Barbell Bench Press Close Grip.png",
    import.meta.url,
  ).href,
  "Barbell Bench Press Wide Grip": new URL(
    "./CHEST/Barbell Bench Press Wide Grip.png",
    import.meta.url,
  ).href,
  "Dumbbell Bench Press": new URL(
    "./CHEST/Dumbbell Bench Press.png",
    import.meta.url,
  ).href,
  "Incline Dumbbell Bench Press": new URL(
    "./CHEST/Incline Dumbbell Bench Press.png",
    import.meta.url,
  ).href,
  "Dumbbell Fly": new URL("./CHEST/Dumbbell Fly.png", import.meta.url)
    .href,
  "Decline Dumbbell Fly": new URL(
    "./CHEST/Decline Dumbbell Fly.png",
    import.meta.url,
  ).href,
  "Medium Cable Fly": new URL(
    "./CHEST/Medium Cable Fly.png",
    import.meta.url,
  ).href,
  "High Cable Fly": new URL(
    "./CHEST/High Cable Fly.png",
    import.meta.url,
  ).href,
  "Low Cable Fly": new URL(
    "./CHEST/Low Cable Fly.png",
    import.meta.url,
  ).href,
  "Chest Press Machine": new URL(
    "./CHEST/Chest Press Machine.png",
    import.meta.url,
  ).href,
  "Chest Fly Machine": new URL(
    "./CHEST/Chest Fly Machine.png",
    import.meta.url,
  ).href,
  "Push-Up Close Grip": new URL(
    "./CHEST/Push-Up Close Grip.png",
    import.meta.url,
  ).href,
  Dips: new URL("./CHEST/Dips.png", import.meta.url).href,
  "Push-Up": new URL("./CHEST/Push-Up.png", import.meta.url).href,
  "Standing Dumbbell Wrist Curl": new URL(
    "./FOREARMS/Standing Dumbbell Wrist Curl.png",
    import.meta.url,
  ).href,
  "Behind Back Barbell Wrist Curl": new URL(
    "./FOREARMS/Behind Back Barbell Wrist Curl.png",
    import.meta.url,
  ).href,
  "Preacher Dumbbell Wrist Curl": new URL(
    "./FOREARMS/Preacher Dumbbell Wrist Curl.png",
    import.meta.url,
  ).href,
  "Side Plank": new URL("./OBLIQUES/Side Plank.png", import.meta.url)
    .href,
  "Hanging Windshield Wipers": new URL(
    "./OBLIQUES/Hanging Windshield Wipers.png",
    import.meta.url,
  ).href,
  "Standing Dumbbell Side Bend": new URL(
    "./OBLIQUES/Standing Dumbbell Side Bend.png",
    import.meta.url,
  ).href,
  "Roman Chair Side Bend": new URL(
    "./OBLIQUES/Roman Chair Side Bend.png",
    import.meta.url,
  ).href,
  "Barbell Back Squat": new URL(
    "./QUADS/Barbell Back Squat.png",
    import.meta.url,
  ).href,
  "Barbell Front Squat": new URL(
    "./QUADS/Barbell Front Squat.png",
    import.meta.url,
  ).href,
  "Barbell Lunge": new URL(
    "./QUADS/Barbell Lunge.png",
    import.meta.url,
  ).href,
  "Goblet Squat": new URL("./QUADS/Goblet Squat.png", import.meta.url)
    .href,
  "Goblet Lunge": new URL("./QUADS/Goblet Lunge.png", import.meta.url)
    .href,
  "Goblet Bulgarian Lunge": new URL(
    "./QUADS/Goblet Bulgarian Lunge.png",
    import.meta.url,
  ).href,
  "Bodyweight Squat": new URL(
    "./QUADS/Bodyweight Squat.png",
    import.meta.url,
  ).href,
  "Dumbbell Lunge": new URL(
    "./QUADS/Dumbbell Lunge.png",
    import.meta.url,
  ).href,
  "Dumbbell Walking Lunges": new URL(
    "./QUADS/Dumbbell Walking Lunges.png",
    import.meta.url,
  ).href,
  "Dumbbell Step Up": new URL(
    "./QUADS/Dumbbell Step Up.png",
    import.meta.url,
  ).href,
  "Step Up": new URL("./QUADS/Step Up.png", import.meta.url).href,
  "Leg Press": new URL("./QUADS/Leg Press.png", import.meta.url).href,
  "Leg Extension": new URL(
    "./QUADS/Leg Extension.png",
    import.meta.url,
  ).href,
  "Bodyweight Lunge": new URL(
    "./QUADS/Bodyweight Lunge.png",
    import.meta.url,
  ).href,
  "Bodyweight Bulgarian Lunge": new URL(
    "./QUADS/Bodyweight Bulgarian Lunge.png",
    import.meta.url,
  ).href,
  Run: new URL("./CARDIO/Run.png", import.meta.url).href,
  "Run on Treadmill": new URL(
    "./CARDIO/Run on Treadmill.png",
    import.meta.url,
  ).href,
  "Bicycle Machine": new URL(
    "./CARDIO/Bicycle Machine.png",
    import.meta.url,
  ).href,
  Cycling: new URL("./CARDIO/Cycling.png", import.meta.url).href,
  "Elliptical Machine": new URL(
    "./CARDIO/Elliptical Machine.png",
    import.meta.url,
  ).href,
  "Step Machine": new URL(
    "./CARDIO/Step Machine.png",
    import.meta.url,
  ).href,
  "Jump Rope": new URL("./CARDIO/Jump Rope.png", import.meta.url)
    .href,
  Burpees: new URL("./CARDIO/Burpees.png", import.meta.url).href,
  "Mountain Climbers": new URL(
    "./CARDIO/Mountain Climbers.png",
    import.meta.url,
  ).href,
  "Battling Rope": new URL(
    "./CARDIO/Battling Rope.png",
    import.meta.url,
  ).href,
  "Walk on Treadmill": new URL(
    "./CARDIO/Walk on Treadmill.png",
    import.meta.url,
  ).href,
  Walk: new URL("./CARDIO/Walk.png", import.meta.url).href,
  "Reverse Bicep Curls": new URL(
    "./BICEPS/Reverse Bicep Curls.png",
    import.meta.url,
  ).href,
  "Bicep Curl to Opposite Shoulder": new URL(
    "./BICEPS/Bicep Curl to Opposite Shoulder.png",
    import.meta.url,
  ).href,
  "Dumbbell Side Curl": new URL(
    "./BICEPS/Dumbbell Side Curl.png",
    import.meta.url,
  ).href,
  "Dumbbell Bicep Curl": new URL(
    "./BICEPS/Dumbbell Bicep Curl.png",
    import.meta.url,
  ).href,
  "Dumbbell Hammer Curl": new URL(
    "./BICEPS/Dumbbell Hammer Curl.png",
    import.meta.url,
  ).href,
  "Incline Dumbbell Hammer Curl": new URL(
    "./BICEPS/Incline Dumbbell Hammer Curl.png",
    import.meta.url,
  ).href,
  "Dumbbell Preacher Curl": new URL(
    "./BICEPS/Dumbbell Preacher Curl.png",
    import.meta.url,
  ).href,
  "Dumbbell Preacher Hammer Curl": new URL(
    "./BICEPS/Dumbbell Preacher Hammer Curl.png",
    import.meta.url,
  ).href,
  "Incline Dumbbell Bicep Curl": new URL(
    "./BICEPS/Incline Dumbbell Bicep Curl.png",
    import.meta.url,
  ).href,
  Crunches: new URL("./ABS/Crunches.png", import.meta.url).href,
  Situps: new URL("./ABS/Situps.png", import.meta.url).href,
  "Decline Crunches": new URL(
    "./ABS/Decline Crunches.png",
    import.meta.url,
  ).href,
  "V-Ups": new URL("./ABS/V-Ups.png", import.meta.url).href,
  "Leg Raises": new URL("./ABS/Leg Raises.png", import.meta.url).href,
  "Reverse Crunch Up": new URL(
    "./ABS/Reverse Crunch Up.png",
    import.meta.url,
  ).href,
  "Hanging Knee Raise": new URL(
    "./ABS/Hanging Knee Raise.png",
    import.meta.url,
  ).href,
  "Hanging Leg Raise": new URL(
    "./ABS/Hanging Leg Raise.png",
    import.meta.url,
  ).href,
  "Toes to Bar": new URL("./ABS/Toes to Bar.png", import.meta.url)
    .href,
  "Ab Station Knee Raise": new URL(
    "./ABS/Ab Station Knee Raise.png",
    import.meta.url,
  ).href,
  "Ab Station Leg Raise": new URL(
    "./ABS/Ab Station Leg Raise.png",
    import.meta.url,
  ).href,
  "Ab Machine": new URL("./ABS/Ab Machine.png", import.meta.url).href,
  "Decline Situps": new URL(
    "./ABS/Decline Situps.png",
    import.meta.url,
  ).href,
  Plank: new URL("./ABS/Plank.png", import.meta.url).href,
  "Single Leg Cable Adduction": new URL(
    "./ADDUCTORS/Single Leg Cable Adduction.png",
    import.meta.url,
  ).href,
  "Adduction Machine": new URL(
    "./ADDUCTORS/Adduction Machine.png",
    import.meta.url,
  ).href,
  "Russian Twist": new URL(
    "./OBLIQUES/Russian Twist.png",
    import.meta.url,
  ).href,
  "Standing Dumbbell Shrugs": new URL(
    "./TRAPS/Standing Dumbbell Shrugs.png",
    import.meta.url,
  ).href,
  "Dumbbell Skull Crusher": new URL(
    "./TRICEPS/Dumbbell Skull Crusher.png",
    import.meta.url,
  ).href,
  "Single Arm Dumbbell Row": new URL(
    "./LATS/Single Arm Dumbbell Row.png",
    import.meta.url,
  ).href,
  Superman: new URL("./LOWER_BACK/Superman.png", import.meta.url)
    .href,
  "Barbell Hip Thrust": new URL(
    "./GLUTES/Barbell Hip Thrust.png",
    import.meta.url,
  ).href,
  "Barbell Deadlift": new URL(
    "./HAMSTRINGS/Barbell Deadlift.png",
    import.meta.url,
  ).href,
  "Calf Raises": new URL("./CALVES/Calf Raises.png", import.meta.url)
    .href,
};

export default IMAGES;
