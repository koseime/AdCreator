/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef AdPreviewCreator_TYPES_H
#define AdPreviewCreator_TYPES_H

#include <thrift/Thrift.h>
#include <thrift/TApplicationException.h>
#include <thrift/protocol/TProtocol.h>
#include <thrift/transport/TTransport.h>

#include <thrift/cxxfunctional.h>




typedef struct _PreviewInfo__isset {
  _PreviewInfo__isset() : productBlob(false), backgroundBlob(false), logoBlob(false), adEntryJsonString(false), title(false), copy(false) {}
  bool productBlob;
  bool backgroundBlob;
  bool logoBlob;
  bool adEntryJsonString;
  bool title;
  bool copy;
} _PreviewInfo__isset;

class PreviewInfo {
 public:

  static const char* ascii_fingerprint; // = "873CB9E5A4C10A7ECE7476FD192F55EE";
  static const uint8_t binary_fingerprint[16]; // = {0x87,0x3C,0xB9,0xE5,0xA4,0xC1,0x0A,0x7E,0xCE,0x74,0x76,0xFD,0x19,0x2F,0x55,0xEE};

  PreviewInfo() : productBlob(), backgroundBlob(), logoBlob(), adEntryJsonString(), title(), copy() {
  }

  virtual ~PreviewInfo() throw() {}

  std::string productBlob;
  std::string backgroundBlob;
  std::string logoBlob;
  std::string adEntryJsonString;
  std::string title;
  std::string copy;

  _PreviewInfo__isset __isset;

  void __set_productBlob(const std::string& val) {
    productBlob = val;
  }

  void __set_backgroundBlob(const std::string& val) {
    backgroundBlob = val;
  }

  void __set_logoBlob(const std::string& val) {
    logoBlob = val;
  }

  void __set_adEntryJsonString(const std::string& val) {
    adEntryJsonString = val;
  }

  void __set_title(const std::string& val) {
    title = val;
  }

  void __set_copy(const std::string& val) {
    copy = val;
  }

  bool operator == (const PreviewInfo & rhs) const
  {
    if (!(productBlob == rhs.productBlob))
      return false;
    if (!(backgroundBlob == rhs.backgroundBlob))
      return false;
    if (!(logoBlob == rhs.logoBlob))
      return false;
    if (!(adEntryJsonString == rhs.adEntryJsonString))
      return false;
    if (!(title == rhs.title))
      return false;
    if (!(copy == rhs.copy))
      return false;
    return true;
  }
  bool operator != (const PreviewInfo &rhs) const {
    return !(*this == rhs);
  }

  bool operator < (const PreviewInfo & ) const;

  uint32_t read(::apache::thrift::protocol::TProtocol* iprot);
  uint32_t write(::apache::thrift::protocol::TProtocol* oprot) const;

};

void swap(PreviewInfo &a, PreviewInfo &b);



#endif