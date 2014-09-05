namespace java com.kosei.adcreator.api


struct PreviewInfo {
  1: binary productBlob,
  2: binary backgroundBlob,
  3: binary logoBlob,
  4: string adEntryJsonString,
  5: string title,
  6: string copy,
}

service AdPreviewCreator {
   void ping(),

   binary createPreview(1:PreviewInfo previewInfo),
}
