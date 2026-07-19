# BLBL Cinematic Pink UI Design

## Goal

Refine BLBL into a coherent Android TV interface that feels cinematic, calm, and easy to operate with a D-pad. Keep the existing feature set and XML View architecture while replacing page-specific styling with shared visual rules.

## Visual language

- Page backdrop: `#07080C`
- Base surface: `#11141B`
- Elevated surface: `#181D27`
- Primary text: `#F7F8FA`
- Secondary text: `#A8B0C0`
- Brand accent: `#FB7299`
- Focus outline: 3dp white
- Card radius: 16dp
- Panel radius: 20dp
- Spacing rhythm: 8 / 16 / 24 / 32 / 48dp
- Focus motion: 160ms; card scale 1.04; button scale 1.02
- Panel motion: 220ms

Pink communicates selection, playback progress, and primary actions. White communicates the current D-pad focus. Selected and focused states must remain distinguishable.

## Navigation

The left navigation is a fixed-width icon rail. It never changes the content viewport width. Focus reveals a floating label beside the icon instead of expanding the rail. Top-level tabs align to the content grid and use a stable underline/indicator without moving the label.

## Content

Media artwork leads the hierarchy. Titles and metadata use consistent fixed-height blocks, while secondary controls remain visually quiet until focused. PGC pages use two columns. Cards are never clipped while scaled.

## Detail, player, and settings

Video detail pages use the cover as a cinematic backdrop with one clear information panel and a compact social-action row. Player controls are grouped by task and quality selection is exposed in the OSD. Settings use grouped rows and chips with the same panel, type, and focus language.

## Accessibility and QA

- Body and supporting text remain at least 16sp.
- Focus never depends on color alone.
- No navigation transition animates width.
- Focus scale is limited to transform/elevation.
- Verify at 1920x1080 in the Android TV emulator.

