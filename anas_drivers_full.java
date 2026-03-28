// ORM class for table 'anas.drivers_full'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Fri Mar 27 15:47:40 UTC 2026
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

public class anas_drivers_full extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  public static interface FieldSetterCommand {    void setField(Object value);  }  protected ResultSet __cur_result_set;
  private Map<String, FieldSetterCommand> setters = new HashMap<String, FieldSetterCommand>();
  private void init0() {
    setters.put("driverId", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.driverId = (Integer)value;
      }
    });
    setters.put("driverRef", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.driverRef = (String)value;
      }
    });
    setters.put("number", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.number = (String)value;
      }
    });
    setters.put("code", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.code = (String)value;
      }
    });
    setters.put("forename", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.forename = (String)value;
      }
    });
    setters.put("surname", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.surname = (String)value;
      }
    });
    setters.put("dob", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.dob = (String)value;
      }
    });
    setters.put("nationality", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.nationality = (String)value;
      }
    });
    setters.put("url", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        anas_drivers_full.this.url = (String)value;
      }
    });
  }
  public anas_drivers_full() {
    init0();
  }
  private Integer driverId;
  public Integer get_driverId() {
    return driverId;
  }
  public void set_driverId(Integer driverId) {
    this.driverId = driverId;
  }
  public anas_drivers_full with_driverId(Integer driverId) {
    this.driverId = driverId;
    return this;
  }
  private String driverRef;
  public String get_driverRef() {
    return driverRef;
  }
  public void set_driverRef(String driverRef) {
    this.driverRef = driverRef;
  }
  public anas_drivers_full with_driverRef(String driverRef) {
    this.driverRef = driverRef;
    return this;
  }
  private String number;
  public String get_number() {
    return number;
  }
  public void set_number(String number) {
    this.number = number;
  }
  public anas_drivers_full with_number(String number) {
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
  public anas_drivers_full with_code(String code) {
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
  public anas_drivers_full with_forename(String forename) {
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
  public anas_drivers_full with_surname(String surname) {
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
  public anas_drivers_full with_dob(String dob) {
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
  public anas_drivers_full with_nationality(String nationality) {
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
  public anas_drivers_full with_url(String url) {
    this.url = url;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof anas_drivers_full)) {
      return false;
    }
    anas_drivers_full that = (anas_drivers_full) o;
    boolean equal = true;
    equal = equal && (this.driverId == null ? that.driverId == null : this.driverId.equals(that.driverId));
    equal = equal && (this.driverRef == null ? that.driverRef == null : this.driverRef.equals(that.driverRef));
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
    if (!(o instanceof anas_drivers_full)) {
      return false;
    }
    anas_drivers_full that = (anas_drivers_full) o;
    boolean equal = true;
    equal = equal && (this.driverId == null ? that.driverId == null : this.driverId.equals(that.driverId));
    equal = equal && (this.driverRef == null ? that.driverRef == null : this.driverRef.equals(that.driverRef));
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
    this.driverId = JdbcWritableBridge.readInteger(1, __dbResults);
    this.driverRef = JdbcWritableBridge.readString(2, __dbResults);
    this.number = JdbcWritableBridge.readString(3, __dbResults);
    this.code = JdbcWritableBridge.readString(4, __dbResults);
    this.forename = JdbcWritableBridge.readString(5, __dbResults);
    this.surname = JdbcWritableBridge.readString(6, __dbResults);
    this.dob = JdbcWritableBridge.readString(7, __dbResults);
    this.nationality = JdbcWritableBridge.readString(8, __dbResults);
    this.url = JdbcWritableBridge.readString(9, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.driverId = JdbcWritableBridge.readInteger(1, __dbResults);
    this.driverRef = JdbcWritableBridge.readString(2, __dbResults);
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
    JdbcWritableBridge.writeInteger(driverId, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(driverRef, 2 + __off, 12, __dbStmt);
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
    JdbcWritableBridge.writeInteger(driverId, 1 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(driverRef, 2 + __off, 12, __dbStmt);
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
        this.driverId = null;
    } else {
    this.driverId = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.driverRef = null;
    } else {
    this.driverRef = Text.readString(__dataIn);
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
    if (null == this.driverId) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.driverId);
    }
    if (null == this.driverRef) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, driverRef);
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
    if (null == this.driverId) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.driverId);
    }
    if (null == this.driverRef) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, driverRef);
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
    __sb.append(FieldFormatter.escapeAndEnclose(driverId==null?"null":"" + driverId, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(driverRef==null?"null":driverRef, delimiters));
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
    __sb.append(FieldFormatter.escapeAndEnclose(driverId==null?"null":"" + driverId, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(driverRef==null?"null":driverRef, delimiters));
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
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.driverId = null; } else {
      this.driverId = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.driverRef = null; } else {
      this.driverRef = __cur_str;
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
    if (__cur_str.equals("null") || __cur_str.length() == 0) { this.driverId = null; } else {
      this.driverId = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "null";
    }
    if (__cur_str.equals("null")) { this.driverRef = null; } else {
      this.driverRef = __cur_str;
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
    anas_drivers_full o = (anas_drivers_full) super.clone();
    return o;
  }

  public void clone0(anas_drivers_full o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new HashMap<String, Object>();
    __sqoop$field_map.put("driverId", this.driverId);
    __sqoop$field_map.put("driverRef", this.driverRef);
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
    __sqoop$field_map.put("driverId", this.driverId);
    __sqoop$field_map.put("driverRef", this.driverRef);
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
