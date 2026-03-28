// ORM class for table 'anas.drivers_full_v'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri Mar 27 16:53:23 UTC 2026
// For connector: org.apache.sqoop.manager.GenericJdbcManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.apache.sqoop.lib.JdbcWritableBridge;
import org.apache.sqoop.lib.DelimiterSet;
import org.apache.sqoop.lib.FieldFormatter;
import org.apache.sqoop.lib.RecordParser;
import org.apache.sqoop.lib.BooleanParser;
import org.apache.sqoop.lib.BlobRef;
import org.apache.sqoop.lib.ClobRef;
import org.apache.sqoop.lib.LargeObjectLoader;
import org.apache.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class anas_drivers_full_v extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  public static interface FieldSetterCommand {    void setField(Object value);  }  protected ResultSet __cur_result_set;
  private Map<String, FieldSetterCommand> setters = new HashMap<String, FieldSetterCommand>();
  private void init0() {
    setters.put("driverid", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.driverid = (Integer)value;
      }
    });
    setters.put("driverref", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.driverref = (String)value;
      }
    });
    setters.put("number", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.number = (String)value;
      }
    });
    setters.put("code", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.code = (String)value;
      }
    });
    setters.put("forename", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.forename = (String)value;
      }
    });
    setters.put("surname", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.surname = (String)value;
      }
    });
    setters.put("dob", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.dob = (String)value;
      }
    });
    setters.put("nationality", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.nationality = (String)value;
      }
    });
    setters.put("url", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full_v.this.url = (String)value;
      }
    });
  }
  public anas_drivers_full_v() {
    init0();
  }
  private Integer driverid;
  public Integer get_driverid() {
    return driverid;
  }
  public void set_driverid(Integer driverid) {
    this.driverid = driverid;
  }
  public anas_drivers_full_v with_driverid(Integer driverid) {
    this.driverid = driverid;
    return this;
  }
  private String driverref;
  public String get_driverref() {
    return driverref;
  }
  public void set_driverref(String driverref) {
    this.driverref = driverref;
  }
  public anas_drivers_full_v with_driverref(String driverref) {
    this.driverref = driverref;
    return this;
  }
  private String number;
  public String get_number() {
    return number;
  }
  public void set_number(String number) {
    this.number = number;
  }
  public anas_drivers_full_v with_number(String number) {
    this.number = number;
    return this;
  }
  private String code;
  public String get_code() {
    return code;
  }
  public void set_code(String code) {
    this.code = code;
  }
  public anas_drivers_full_v with_code(String code) {
    this.code = code;
    return this;
  }
  private String forename;
  public String get_forename() {
    return forename;
  }
  public void set_forename(String forename) {
    this.forename = forename;
  }
  public anas_drivers_full_v with_forename(String forename) {
    this.forename = forename;
    return this;
  }
  private String surname;
  public String get_surname() {
    return surname;
  }
  public void set_surname(String surname) {
    this.surname = surname;
  }
  public anas_drivers_full_v with_surname(String surname) {
    this.surname = surname;
    return this;
  }
  private String dob;
  public String get_dob() {
    return dob;
  }
  public void set_dob(String dob) {
    this.dob = dob;
  }
  public anas_drivers_full_v with_dob(String dob) {
    this.dob = dob;
    return this;
  }
  private String nationality;
  public String get_nationality() {
    return nationality;
  }
  public void set_nationality(String nationality) {
    this.nationality = nationality;
  }
  public anas_drivers_full_v with_nationality(String nationality) {
    this.nationality = nationality;
    return this;
  }
  private String url;
  public String get_url() {
    return url;
  }
  public void set_url(String url) {
    this.url = url;
  }
  public anas_drivers_full_v with_url(String url) {
    this.url = url;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof anas_drivers_full_v)) {
      return false;
    }
    anas_drivers_full_v that = (anas_drivers_full_v) o;
    boolean equal = true;
    equal = equal && (this.driverid == null ? that.driverid == null : this.driverid.equals(that.driverid));
    equal = equal && (this.driverref == null ? that.driverref == null : this.driverref.equals(that.driverref));
    equal = equal && (this.number == null ? that.number == null : this.number.equals(that.number));
    equal = equal && (this.code == null ? that.code == null : this.code.equals(that.code));
    equal = equal && (this.forename == null ? that.forename == null : this.forename.equals(that.forename));
    equal = equal && (this.surname == null ? that.surname == null : this.surname.equals(that.surname));
    equal = equal && (this.dob == null ? that.dob == null : this.dob.equals(that.dob));
    equal = equal && (this.nationality == null ? that.nationality == null : this.nationality.equals(that.nationality));
    equal = equal && (this.url == null ? that.url == null : this.url.equals(that.url));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof anas_drivers_full_v)) {
      return false;
    }
    anas_drivers_full_v that = (anas_drivers_full_v) o;
    boolean equal = true;
    equal = equal && (this.driverid == null ? that.driverid == null : this.driverid.equals(that.driverid));
    equal = equal && (this.driverref == null ? that.driverref == null : this.driverref.equals(that.driverref));
    equal = equal && (this.number == null ? that.number == null : this.number.equals(that.number));
    equal = equal && (this.code == null ? that.code == null : this.code.equals(that.code));
    equal = equal && (this.forename == null ? that.forename == null : this.forename.equals(that.forename));
    equal = equal && (this.surname == null ? that.surname == null : this.surname.equals(that.surname));
    equal = equal && (this.dob == null ? that.dob == null : this.dob.equals(that.dob));
    equal = equal && (this.nationality == null ? that.nationality == null : this.nationality.equals(that.nationality));
    equal = equal && (this.url == null ? that.url == null : this.url.equals(that.url));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.driverid = JdbcWritableBridge.readInteger(1, __dbResults);
    this.driverref = JdbcWritableBridge.readString(2, __dbResults);
    this.number = JdbcWritableBridge.readString(3, __dbResults);
    this.code = JdbcWritableBridge.readString(4, __dbResults);
    this.forename = JdbcWritableBridge.readString(5, __dbResults);
    this.surname = JdbcWritableBridge.readString(6, __dbResults);
    this.dob = JdbcWritableBridge.readString(7, __dbResults);
    this.nationality = JdbcWritableBridge.readString(8, __dbResults);
    this.url = JdbcWritableBridge.readString(9, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.driverid = JdbcWritableBridge.readInteger(1, __dbResults);
    this.driverref = JdbcWritableBridge.readString(2, __dbResults);
    this.number = JdbcWritableBridge.readString(3, __dbResults);
    this.code = JdbcWritableBridge.readString(4, __dbResults);
    this.forename = JdbcWritableBridge.readString(5, __dbResults);
    this.surname = JdbcWritableBridge.readString(6, __dbResults);
    this.dob = JdbcWritableBridge.readString(7, __dbResults);
    this.nationality = JdbcWritableBridge.readString(8, __dbResults);
    this.url = JdbcWritableBridge.readString(9, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(driverid, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(driverref, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(number, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(code, 4 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(forename, 5 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(surname, 6 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(dob, 7 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(nationality, 8 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(url, 9 + __off, 12, __dbStmt);
    return 9;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeInteger(driverid, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(driverref, 2 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(number, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(code, 4 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(forename, 5 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(surname, 6 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(dob, 7 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(nationality, 8 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(url, 9 + __off, 12, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.driverid = null;
    } else {
    this.driverid = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.driverref = null;
    } else {
    this.driverref = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.number = null;
    } else {
    this.number = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.code = null;
    } else {
    this.code = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.forename = null;
    } else {
    this.forename = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.surname = null;
    } else {
    this.surname = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.dob = null;
    } else {
    this.dob = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.nationality = null;
    } else {
    this.nationality = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.url = null;
    } else {
    this.url = Text.readString(__dataIn);
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.driverid) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.driverid);
    }
    if (null == this.driverref) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, driverref);
    }
    if (null == this.number) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, number);
    }
    if (null == this.code) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, code);
    }
    if (null == this.forename) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, forename);
    }
    if (null == this.surname) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, surname);
    }
    if (null == this.dob) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, dob);
    }
    if (null == this.nationality) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, nationality);
    }
    if (null == this.url) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, url);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.driverid) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.driverid);
    }
    if (null == this.driverref) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, driverref);
    }
    if (null == this.number) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, number);
    }
    if (null == this.code) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, code);
    }
    if (null == this.forename) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, forename);
    }
    if (null == this.surname) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, surname);
    }
    if (null == this.dob) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, dob);
    }
    if (null == this.nationality) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, nationality);
    }
    if (null == this.url) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, url);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(driverid==null?"null":"" + driverid, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(driverref==null?"null":driverref, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(number==null?"null":number, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(code==null?"null":code, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(forename==null?"null":forename, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(surname==null?"null":surname, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(dob==null?"null":dob, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(nationality==null?"null":nationality, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(url==null?"null":url, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(driverid==null?"null":"" + driverid, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(driverref==null?"null":driverref, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(number==null?"null":number, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(code==null?"null":code, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(forename==null?"null":forename, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(surname==null?"null":surname, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(dob==null?"null":dob, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(nationality==null?"null":nationality, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(url==null?"null":url, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 44, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.driverid = null; } else {
      this.driverid = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.driverref = null; } else {
      this.driverref = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.number = null; } else {
      this.number = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.code = null; } else {
      this.code = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.forename = null; } else {
      this.forename = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.surname = null; } else {
      this.surname = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.dob = null; } else {
      this.dob = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.nationality = null; } else {
      this.nationality = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.url = null; } else {
      this.url = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.driverid = null; } else {
      this.driverid = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.driverref = null; } else {
      this.driverref = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.number = null; } else {
      this.number = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.code = null; } else {
      this.code = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.forename = null; } else {
      this.forename = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.surname = null; } else {
      this.surname = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.dob = null; } else {
      this.dob = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.nationality = null; } else {
      this.nationality = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.url = null; } else {
      this.url = __cur_str;
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    anas_drivers_full_v o = (anas_drivers_full_v) super.clone();
    return o;
  }

  public void clone0(anas_drivers_full_v o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new HashMap<String, Object>();
    __sqoop$field_map.put("driverid", this.driverid);
    __sqoop$field_map.put("driverref", this.driverref);
    __sqoop$field_map.put("number", this.number);
    __sqoop$field_map.put("code", this.code);
    __sqoop$field_map.put("forename", this.forename);
    __sqoop$field_map.put("surname", this.surname);
    __sqoop$field_map.put("dob", this.dob);
    __sqoop$field_map.put("nationality", this.nationality);
    __sqoop$field_map.put("url", this.url);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("driverid", this.driverid);
    __sqoop$field_map.put("driverref", this.driverref);
    __sqoop$field_map.put("number", this.number);
    __sqoop$field_map.put("code", this.code);
    __sqoop$field_map.put("forename", this.forename);
    __sqoop$field_map.put("surname", this.surname);
    __sqoop$field_map.put("dob", this.dob);
    __sqoop$field_map.put("nationality", this.nationality);
    __sqoop$field_map.put("url", this.url);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if (!setters.containsKey(__fieldName)) {
      throw new RuntimeException("No such field:"+__fieldName);
    }
    setters.get(__fieldName).setField(__fieldVal);
  }

}
