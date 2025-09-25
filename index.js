import { NativeModules } from "react-native";

const { Pax } = NativeModules;

export default {
  FULL_CUT: 0,
  PARTIAL_CUT: 1,

  printStr(text, cutMode) {
    Pax.printStr(text, cutMode === undefined ? 0 : cutMode);
  },
  printText(text, large, lineSpace, invert) {
    Pax.printText(text, large, lineSpace, !!invert);
  },
  printQRCode(text) {
    Pax.printQRCode(text);
  },
  printBarcode(text) {
    Pax.printBarcode(text);
  },
  init() {
    Pax.init();
  },
  start() {
    Pax.start();
  },
  openDrawer() {
    return Pax.openDrawer();
  },
  printBitmap(base64, cutMode) {
    Pax.printBitmap(base64, cutMode === undefined ? 0 : cutMode);
  },
};
